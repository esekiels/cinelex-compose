/*
 * Cinelex
 * MovieServiceTest
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.network

import co.esekiels.cinelex.core.network.service.MovieService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieServiceTest : ApiAbstractTest<MovieService>() {

    private lateinit var service: MovieService

    @Before
    fun initService() {
        service = createService(MovieService::class.java)
    }

    @Test
    fun fetchMoviesFromNetworkTest() = runTest {
        enqueueResponse("MovieResponse.json")
        val response = service.fetchMovies("movie/now_playing")
        assertTrue(response.isSuccessful)
        assertEquals(2, response.body()!!.results.size)
    }
}
