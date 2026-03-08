/*
 * Cinelex
 * MovieDetailsEntity
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.esekiels.cinelex.core.model.Credits
import co.esekiels.cinelex.core.model.Genre
import co.esekiels.cinelex.core.model.VideoResponse

@Entity
data class MovieDetailsEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    val overview: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val runtime: Int?,
    val genres: List<Genre>,
    val credits: Credits?,
    val videos: VideoResponse?,
)
