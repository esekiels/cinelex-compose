/*
 * Cinelex
 * GenreDao
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.esekiels.cinelex.core.database.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun saveGenres(genres: List<GenreEntity>)

	@Query("SELECT * FROM GenreEntity ORDER BY id ASC")
	suspend fun fetchGenres(): List<GenreEntity>

	@Query("SELECT * FROM GenreEntity ORDER BY id ASC")
	fun fetchGenresFlow(): Flow<List<GenreEntity>>
}

