/*
 * Cinelex
 * MovieEntity
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["id", "category"])
data class MovieEntity(
    val id: Int,
    val title: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    val category: String
)
