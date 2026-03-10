/*
 * Cinelex
 * Routes
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface CinelexRoute : NavKey {
    @Serializable
    data object Home : CinelexRoute

    @Serializable
    data object Search : CinelexRoute

    @Serializable
    data class Details(val movieId: Int) : CinelexRoute
}
