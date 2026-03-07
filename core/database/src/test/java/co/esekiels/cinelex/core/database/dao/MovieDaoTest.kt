/*
 * Cinelex
 * MovieDaoTest
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.database.dao

import co.esekiels.cinelex.core.database.LocalDatabase
import co.esekiels.cinelex.core.database.entity.mapper.toEntities
import co.esekiels.cinelex.core.testing.MovieStubs
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieDaoTest : LocalDatabase() {

    private lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        movieDao = db.movieDao()
    }

    @Test
    fun shouldInsertAndLoadMovieList() = runTest {
        val category = "popular"
        val entities = MovieStubs.toEntities(category)

        movieDao.saveMovies(entities)
        val result = movieDao.fetchMovieListByCategory(category)

        assertEquals(entities.size, result.size)
        assertEquals(entities.first().id, result.first().id)
    }
}
