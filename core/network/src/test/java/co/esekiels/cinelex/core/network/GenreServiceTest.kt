/*
 * Cinelex
 * GenreServiceTest
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.network

import co.esekiels.cinelex.core.network.service.GenreService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GenreServiceTest: ApiAbstractTest<GenreService>() {
	
	private lateinit var service: GenreService
	
	@Before
	fun initService() {
		service = createService(GenreService::class.java)
	}
	
	@Test
	fun fetchGenresFromNetworkTest() = runTest {
		enqueueResponse("GenreResponse.json")
		val response = service.fetchGenres()
		assertTrue(response.isSuccessful)
		assertEquals(19, response.body()!!.results.size)
	}
}
