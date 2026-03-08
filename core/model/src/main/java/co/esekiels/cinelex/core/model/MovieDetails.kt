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

    companion object {
        val stub = MovieDetails(
            id = 278,
            title = "The Shawshank Redemption",
            backdropPath = "/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg",
            posterPath = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
            overview = "Imprisoned in the 1940s for the double murder of his wife and her lover, " +
                "upstanding banker Andy Dufresne begins a new life at the Shawshank prison, " +
                "where he puts his accounting skills to work for an amoral warden. During his " +
                "long stretch in prison, Dufresne comes to be admired by the other inmates -- " +
                "including an older prisoner named Red -- for his integrity and unquenchable " +
                "sense of hope.",
            voteAverage = 8.7,
            releaseDate = "1994-09-23",
            runtime = 142,
            genres = listOf(
                Genre(id = 18, name = "Drama"),
                Genre(id = 80, name = "Crime"),
            ),
            credits = Credits(
                cast = listOf(
                    Cast(id = 504, name = "Tim Robbins", character = "Andy Dufresne"),
                    Cast(id = 192, name = "Morgan Freeman", character = "Ellis Boyd 'Red' Redding"),
                    Cast(id = 4029, name = "Bob Gunton", character = "Warden Samuel Norton"),
                    Cast(id = 6574, name = "William Sadler", character = "Heywood"),
                    Cast(id = 9857, name = "Clancy Brown", character = "Captain Byron Hadley"),
                ),
                crew = listOf(
                    Crew(id = 4027, name = "Frank Darabont", job = "Director"),
                    Crew(id = 4027, name = "Frank Darabont", job = "Screenplay"),
                    Crew(id = 4028, name = "Niki Marvin", job = "Producer"),
                ),
            ),
            videos = VideoResponse(
                results = listOf(
                    Video(
                        id = "1",
                        key = "PLl99DlL6b4",
                        name = "Official Trailer",
                        site = "YouTube",
                        type = "Trailer",
                    ),
                ),
            ),
        )
    }
}

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
