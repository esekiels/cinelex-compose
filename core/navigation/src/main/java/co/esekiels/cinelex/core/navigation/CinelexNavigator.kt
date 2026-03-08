/*
 * Cinelex
 * CinelexNavigator
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

interface CinelexNavigator {
    fun navigate(route: CinelexRoute)
    fun navigateUp(): Boolean
}

class CinelexNavigatorImpl(
    private val backStack: NavBackStack<NavKey>,
) : CinelexNavigator {

    override fun navigate(route: CinelexRoute) {
        backStack.add(route)
    }

    override fun navigateUp(): Boolean {
        return if (backStack.size > 1) {
            backStack.removeLastOrNull() != null
        } else {
            false
        }
    }
}
