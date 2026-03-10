/*
 * Cinelex
 * Credits
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Credits(
    val cast: List<Cast>,
    val crew: List<Crew>,
)

@Immutable
@Serializable
data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    @SerialName("profile_path")
    val profilePath: String? = null,
) {
    val profileUrl: String? get() = profilePath?.let { "$PROFILE_IMAGE_BASE_URL$it" }
}

@Immutable
@Serializable
data class Crew(
    val id: Int,
    val name: String,
    val job: String,
)

private const val PROFILE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185"
