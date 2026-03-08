/*
 * Cinelex
 * MovieDetailsDaoTest
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.database.dao

import co.esekiels.cinelex.core.database.LocalDatabase
import co.esekiels.cinelex.core.database.entity.mapper.toEntity
import co.esekiels.cinelex.core.model.MovieDetails
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MovieDetailsDaoTest : LocalDatabase() {

    private lateinit var dao: MovieDetailsDao

    @Before
    fun setup() {
        dao = db.movieDetailsDao()
    }

    @Test
    fun shouldInsertAndLoadMovieDetails() = runTest {
        val entity = MovieDetails.stub.toEntity()

        dao.saveMovieDetails(entity)
        val result = dao.fetchMovieDetailsById(entity.id)

        assertNotNull(result)
        assertEquals(entity.id, result!!.id)
        assertEquals(entity.title, result.title)
    }
}
