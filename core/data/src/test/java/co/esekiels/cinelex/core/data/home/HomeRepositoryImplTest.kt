/*
 * Cinelex
 * HomeRepositoryImplTest
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.data.home

import co.esekiels.cinelex.core.database.dao.MovieDao
import co.esekiels.cinelex.core.database.entity.mapper.toEntities
import co.esekiels.cinelex.core.datastore.UserPreferencesDataSource
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.network.ApiResponse
import co.esekiels.cinelex.core.network.model.MovieResponse
import co.esekiels.cinelex.core.network.service.MovieClient
import co.esekiels.cinelex.core.testing.MainCoroutinesRule
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

class HomeRepositoryImplTest {

    private lateinit var repository: HomeRepositoryImpl
    private val client: MovieClient = mock()
    private val dao: MovieDao = mock()
    private val userPreferencesDataSource: UserPreferencesDataSource = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        repository = HomeRepositoryImpl(
            client = client,
            dao = dao,
            userPreferencesDataSource = userPreferencesDataSource,
            ioDispatcher = coroutinesRule.testDispatcher,
        )
    }

    @Test
    fun shouldFetchMoviesFromNetwork() = runTest {
        val category = "movie/popular"
        val mockResponse = MovieResponse(results = MovieStubs)
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
}
