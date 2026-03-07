/*
 * Cinelex
 * CinelexDispatchers
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.common.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: CinelexDispatchers)

enum class CinelexDispatchers {
    IO
}
