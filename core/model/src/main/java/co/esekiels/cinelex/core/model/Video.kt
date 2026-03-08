/*
 * Cinelex
 * Video
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class VideoResponse(
    val results: List<Video>,
)

@Immutable
@Serializable
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
) {
    val youtubeUrl: String?
        get() = if (site == "YouTube") "https://www.youtube.com/watch?v=$key" else null
}
