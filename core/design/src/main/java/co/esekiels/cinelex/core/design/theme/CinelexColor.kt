/*
 * Cinelex
 * CinelexColor
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

package co.esekiels.cinelex.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

val CinelexPrimary = Color(0xFF075E54)

val DarkBackground = Color(0xFF111B21)
val DarkTextPrimary = Color(0xFFE9EDEF)
val DarkTextSecondary = Color(0xFF8696A0)

val LightBackground = Color(0xFFFFFFFF)
val LightTextPrimary = Color(0xFF111B21)
val LightTextSecondary = Color(0xFF667781)

@Immutable
data class CinelexColor(
    val primary: Color,
    val background: Color,
    val textPrimary: Color,
    val textSecondary: Color,
)

internal val DarkCinelexColors = CinelexColor(
    primary = CinelexPrimary,
    background = DarkBackground,
    textPrimary = DarkTextPrimary,
    textSecondary = DarkTextSecondary,
)

internal val LightCinelexColors = CinelexColor(
    primary = CinelexPrimary,
    background = LightBackground,
    textPrimary = LightTextPrimary,
    textSecondary = LightTextSecondary,
)
