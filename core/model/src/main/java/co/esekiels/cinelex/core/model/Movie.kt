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

@Immutable
@Serializable
data class Movie(
	val id: Int,
	val title: String,
	@SerialName("backdrop_path")
	val backdropPath: String? = null,
	@SerialName("poster_path")
	val posterPath: String? = null,
) {
	val posterUrl: String? get() = posterPath?.let { "$IMAGE_BASE_URL$it" }
	val backdropUrl: String? get() = backdropPath?.let { "$IMAGE_BASE_URL$it" }
}

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
