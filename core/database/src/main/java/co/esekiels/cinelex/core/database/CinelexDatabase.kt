/*
 * Cinelex
 * CinelexDatabase
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.esekiels.cinelex.core.database.dao.MovieDao
import co.esekiels.cinelex.core.database.entity.MovieEntity

@Database(
	entities = [MovieEntity::class],
	version = 1,
	exportSchema = false
)
abstract class CinelexDatabase: RoomDatabase() {
	abstract fun movieDao(): MovieDao
}
