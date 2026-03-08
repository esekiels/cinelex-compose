/*
 * Cinelex
 * CinelexDatabase
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.esekiels.cinelex.core.database.converter.CreditsConverter
import co.esekiels.cinelex.core.database.converter.GenreListConverter
import co.esekiels.cinelex.core.database.converter.VideoResponseConverter
import co.esekiels.cinelex.core.database.dao.MovieDao
import co.esekiels.cinelex.core.database.entity.MovieDetailsEntity
import co.esekiels.cinelex.core.database.entity.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    GenreListConverter::class,
    CreditsConverter::class,
    VideoResponseConverter::class,
)
abstract class CinelexDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
