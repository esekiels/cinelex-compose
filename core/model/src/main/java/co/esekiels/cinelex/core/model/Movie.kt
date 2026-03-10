/*
 * Cinelex
 * Movie
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Locale

@Immutable
@Serializable
data class Movie(
    val id: Int,
    val title: String,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String = "",
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,
    val genres: List<Genre>? = null,
) {
    val posterUrl: String? get() = posterPath?.let { "$IMAGE_BASE_URL$it" }
    val backdropUrl: String? get() = backdropPath?.let { "$IMAGE_BASE_URL$it" }
    val rating: String get() = String.format(Locale.US, "%.1f", voteAverage)
}

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
