package co.esekiels.cinelex.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.esekiels.cinelex.core.design.component.CinelexAppBar
import co.esekiels.cinelex.core.design.component.CinelexLoading
import co.esekiels.cinelex.core.design.theme.CinelexTheme

/*
 * Cinelex
 * HomeScreen
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

@Composable
fun HomeScreen(
    isDarkTheme: Boolean = false,
    onLanguageClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CinelexAppBar(
            isDarkTheme = isDarkTheme,
            onLanguageClick = onLanguageClick,
            onThemeClick = onThemeClick,
        )
        Box(modifier = Modifier.fillMaxSize()) {
            CinelexLoading()
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    CinelexTheme {
        HomeScreen()
    }
}
