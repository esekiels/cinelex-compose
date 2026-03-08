/*
 * Cinelex
 * LocalComposeNavigator
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalComposeNavigator: ProvidableCompositionLocal<CinelexNavigator> =
    compositionLocalOf {
        error(
            "No CinelexNavigator provided! " +
                "Make sure to wrap all usages in CompositionLocalProvider.",
        )
    }
