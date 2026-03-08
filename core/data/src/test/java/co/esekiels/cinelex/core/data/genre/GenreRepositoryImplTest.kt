/*
 * Cinelex
 * GenreRepositoryImplTest
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.data.genre

import co.esekiels.cinelex.core.database.dao.GenreDao
import co.esekiels.cinelex.core.database.entity.mapper.toEntities
import co.esekiels.cinelex.core.datastore.UserPreferencesDataSource
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.network.ApiResponse
import co.esekiels.cinelex.core.network.model.GenreResponse
import co.esekiels.cinelex.core.network.service.GenreClient
import co.esekiels.cinelex.core.testing.GenreStubs
import co.esekiels.cinelex.core.testing.MainCoroutinesRule
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

class GenreRepositoryImplTest {
	
	private lateinit var repository: GenreRepository
	private val client: GenreClient = mock()
	private val dao: GenreDao = mock()
	private val userPreferencesDataSource: UserPreferencesDataSource = mock()
	
	@get:Rule
	val coroutinesRule = MainCoroutinesRule()
	
	@Before
	fun setup() {
		repository = GenreRepositoryImpl(
			client = client,
			dao = dao,
			userPreferencesDataSource = userPreferencesDataSource,
			ioDispatchers = coroutinesRule.testDispatcher
		)
	}
	
	@Test
	fun shouldFetchGenresFromNetwork() = runTest {
		val mockResponse = GenreResponse(results = GenreStubs)
		whenever(userPreferencesDataSource.getLanguage()).thenReturn(Language.ENGLISH.code)
		whenever(client.fetchGenres(Language.ENGLISH.tmdbCode))
			.thenReturn(ApiResponse.Success(mockResponse))
		whenever(dao.fetchGenres())
			.thenReturn(GenreStubs.toEntities())
		
		val result = repository.fetchGenres()
		assertEquals(GenreStubs.size, result.size)
		assertEquals(GenreStubs.first().id, result.first().id)
		verify(client, atLeastOnce()).fetchGenres(Language.ENGLISH.tmdbCode)
		verify(dao, atLeastOnce()).saveGenres(mockResponse.results.toEntities())
	}
	
	@Test
	fun shouldFetchGenresFromDatabaseOnNetworkError() = runTest {
		whenever(userPreferencesDataSource.getLanguage()).thenReturn(Language.ENGLISH.code)
		whenever(client.fetchGenres(Language.ENGLISH.tmdbCode))
			.thenReturn(ApiResponse.NetworkError(IOException("No internet")))
		whenever(dao.fetchGenres())
			.thenReturn(GenreStubs.toEntities())
		
		val result = repository.fetchGenres()
		
		assertEquals(GenreStubs.size, result.size)
		verify(dao, atLeastOnce()).fetchGenres()
	}
}
