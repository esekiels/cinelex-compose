/*
 * Cinelex
 * CinelexException
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.common

data class CinelexException(
    val code: String,
    override val message: String
) : Exception()
