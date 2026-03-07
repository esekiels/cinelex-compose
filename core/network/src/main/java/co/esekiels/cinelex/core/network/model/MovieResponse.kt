/*
 * Cinelex
 * MovieResponse
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.network.model

import co.esekiels.cinelex.core.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val results: List<Movie>
)
