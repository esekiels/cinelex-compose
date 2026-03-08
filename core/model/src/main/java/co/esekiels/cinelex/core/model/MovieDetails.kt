/*
 * Cinelex
 * MovieDetails
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

@Immutable
@Serializable
data class MovieDetails(
    val id: Int,
    val title: String,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    val overview: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("release_date")
    val releaseDate: String,
    val runtime: Int? = null,
    val genres: List<Genre>,
    val credits: Credits? = null,
    val videos: VideoResponse? = null,
) {
    val backdropUrl: String? get() = backdropPath?.let { "$IMAGE_BASE_URL$it" }

    val posterUrl: String? get() = posterPath?.let { "$IMAGE_BASE_URL$it" }

    val genreFormatted: String
        get() = genres.takeIf { it.isNotEmpty() }?.joinToString(", ") { it.name } ?: "N/A"

    val releaseYearFormatted: String
        get() = releaseDate.takeIf { it.isNotEmpty() }?.take(4) ?: "N/A"

    val durationFormatted: String
        get() = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "N/A"

    val scoreRating: String
        get() = "%.1f".format(voteAverage)

    fun isStarFilled(index: Int): Boolean =
        index < voteAverage.roundToInt()

    val cast: List<Cast>? get() = credits?.cast

    val directors: List<Crew>?
        get() = credits?.crew?.filter { it.job == "Director" }

    val producers: List<Crew>?
        get() = credits?.crew?.filter { it.job == "Producer" }

    val screenwriters: List<Crew>?
        get() = credits?.crew?.filter { it.job == "Screenplay" || it.job == "Writer" }

    val youtubeTrailers: List<Video>?
        get() = videos?.results?.filter { it.site == "YouTube" && it.type == "Trailer" }
}

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
