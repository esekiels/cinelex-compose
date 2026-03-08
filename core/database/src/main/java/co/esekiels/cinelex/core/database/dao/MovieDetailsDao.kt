/*
 * Cinelex
 * MovieDetailsDao
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.esekiels.cinelex.core.database.entity.MovieDetailsEntity

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieDetails(movieDetails: MovieDetailsEntity)

    @Query("SELECT * FROM MovieDetailsEntity WHERE id = :id")
    suspend fun fetchMovieDetailsById(id: Int): MovieDetailsEntity?

}
