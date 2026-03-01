package co.esekiels.cinelex.core.design.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

private val LocalColors = compositionLocalOf<CinelexColor> {
    error("No CinelexColor provided. Wrap your composable with CinelexTheme.")
}

@Composable
fun CinelexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkCinelexColors else LightCinelexColors
    val background = if (darkTheme) DarkCinelexBackground else LightCinelexBackground

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalBackgroundTheme provides background,
        LocalTypography provides CinelexTypography(),
    ) {
        Box(
            modifier = Modifier
                .background(background.color)
        ) {
            content()
        }
    }
}

object CinelexTheme {
    val colors: CinelexColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val background: CinelexBackground
        @Composable
        @ReadOnlyComposable
        get() = LocalBackgroundTheme.current

    val typography: CinelexTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}
