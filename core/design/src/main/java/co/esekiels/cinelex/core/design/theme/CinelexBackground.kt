package co.esekiels.cinelex.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class CinelexBackground(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified,
)

internal val DarkCinelexBackground = CinelexBackground(
    color = DarkBackground,
    tonalElevation = 0.dp,
)

internal val LightCinelexBackground = CinelexBackground(
    color = LightBackground,
    tonalElevation = 0.dp,
)

val LocalBackgroundTheme = staticCompositionLocalOf { CinelexBackground() }
