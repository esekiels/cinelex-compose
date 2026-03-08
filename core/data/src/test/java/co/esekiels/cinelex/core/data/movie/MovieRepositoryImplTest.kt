/*
 * Cinelex
 * MovieRepositoryImplTest
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.data.movie

import co.esekiels.cinelex.core.database.dao.MovieDao
import co.esekiels.cinelex.core.database.entity.mapper.toEntities
import co.esekiels.cinelex.core.datastore.UserPreferencesDataSource
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.network.ApiResponse
import co.esekiels.cinelex.core.network.model.MovieResponse
import co.esekiels.cinelex.core.network.service.MovieClient
import co.esekiels.cinelex.core.testing.MainCoroutinesRule
import co.esekiels.cinelex.core.testing.MovieDetailsStub
import co.esekiels.cinelex.core.testing.MovieStubs
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

class MovieRepositoryImplTest {

    private lateinit var repository: MovieRepository
    private val client: MovieClient = mock()
    private val dao: MovieDao = mock()
    private val userPreferencesDataSource: UserPreferencesDataSource = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(
            client = client,
            dao = dao,
            userPreferencesDataSource = userPreferencesDataSource,
            ioDispatcher = coroutinesRule.testDispatcher,
        )
    }

    @Test
    fun shouldFetchMoviesFromNetwork() = runTest {
        val category = "movie/popular"
        val mockResponse = MovieResponse(page= 1, totalPages = 10, results = MovieStubs)
        whenever(userPreferencesDataSource.getLanguage()).thenReturn(Language.ENGLISH.code)
        whenever(client.fetchMovies(category, Language.ENGLISH.tmdbCode))
            .thenReturn(ApiResponse.Success(mockResponse))
        whenever(dao.fetchMovieListByCategory(category))
            .thenReturn(MovieStubs.toEntities(category))

        val result = repository.fetchPopular()

        assertEquals(MovieStubs.size, result.size)
        assertEquals(MovieStubs.first().id, result.first().id)
        verify(client, atLeastOnce()).fetchMovies(category, Language.ENGLISH.tmdbCode)
        verify(dao, atLeastOnce()).clearByCategory(category)
        verify(dao, atLeastOnce()).saveMovies(mockResponse.results.toEntities(category))
    }

    @Test
    fun shouldFetchMoviesFromDatabaseOnNetworkError() = runTest {
        val category = "movie/popular"
        whenever(userPreferencesDataSource.getLanguage()).thenReturn(Language.ENGLISH.code)
        whenever(client.fetchMovies(category, Language.ENGLISH.tmdbCode))
            .thenReturn(ApiResponse.NetworkError(IOException("No internet")))
        whenever(dao.fetchMovieListByCategory(category))
            .thenReturn(MovieStubs.toEntities(category))

        val result = repository.fetchPopular()

        assertEquals(MovieStubs.size, result.size)
        verify(dao, atLeastOnce()).fetchMovieListByCategory(category)
    }

    @Test
    fun shouldFetchDetailsFromNetwork() = runTest {
        val stub = MovieDetailsStub
        whenever(userPreferencesDataSource.getLanguage()).thenReturn(Language.ENGLISH.code)
        whenever(client.fetchDetails(stub.id, Language.ENGLISH.tmdbCode))
            .thenReturn(ApiResponse.Success(stub))

        val result = repository.fetchMovieDetails(stub.id)

        assertEquals(stub.id, result.id)
        assertEquals(stub.title, result.title)
        verify(client, atLeastOnce()).fetchDetails(stub.id, Language.ENGLISH.tmdbCode)
        verify(dao, atLeastOnce()).saveMovieDetails(stub.toEntities())
    }

    @Test
    fun shouldFetchDetailsFromDatabaseOnNetworkError() = runTest {
        val stub = MovieDetailsStub
        whenever(userPreferencesDataSource.getLanguage()).thenReturn(Language.ENGLISH.code)
        whenever(client.fetchDetails(stub.id, Language.ENGLISH.tmdbCode))
            .thenReturn(ApiResponse.NetworkError(IOException("No internet")))
        whenever(dao.fetchMovieDetailsById(stub.id))
            .thenReturn(stub.toEntities())

        val result = repository.fetchMovieDetails(stub.id)

        assertEquals(stub.id, result.id)
        verify(dao, atLeastOnce()).fetchMovieDetailsById(stub.id)
    }
}
