/*
 * Cinelex
 * MovieDao
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.esekiels.cinelex.core.database.entity.MovieDetailsEntity
import co.esekiels.cinelex.core.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE category = :category")
    suspend fun fetchMovieListByCategory(category: String): List<MovieEntity>

    @Query("DELETE FROM MovieEntity WHERE category = :category")
    suspend fun clearByCategory(category: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieDetails(movieDetails: MovieDetailsEntity)

    @Query("SELECT * FROM MovieDetailsEntity WHERE id = :id")
    suspend fun fetchMovieDetailsById(id: Int): MovieDetailsEntity?
}
