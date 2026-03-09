/*
 * Cinelex
 * GenreResponse
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.network.model

import co.esekiels.cinelex.core.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
	@SerialName("genres")
    val results: List<Genre>
)
