/*
 * Cinelex
 * GenreDaoTest
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.database.dao

import co.esekiels.cinelex.core.database.LocalDatabase
import co.esekiels.cinelex.core.database.entity.mapper.toEntities
import co.esekiels.cinelex.core.testing.GenreStubs
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GenreDaoTest: LocalDatabase() {
	
	private lateinit var dao: GenreDao
	
	@Before
	fun setup() {
		dao = db.genreDao()
	}
	
	@Test
	fun shouldInsertAndLoadGenreList() = runTest {
		val entities = GenreStubs.toEntities()
		
		dao.saveGenres(entities)
		val result = dao.fetchGenres()
		
		assertEquals(entities.size, result.size)
		assertEquals(entities.first().id, result.first().id)
	}
}
