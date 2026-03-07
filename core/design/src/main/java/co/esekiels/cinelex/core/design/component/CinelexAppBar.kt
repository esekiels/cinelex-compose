/*
 * Cinelex
 * CinelexAppBar
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

package co.esekiels.cinelex.core.design.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.esekiels.cinelex.core.design.R
import co.esekiels.cinelex.core.design.theme.CinelexTheme

@Composable
fun CinelexAppBar(
    isDarkTheme: Boolean = false,
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = CinelexTheme.typography.headingSmall,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CinelexTheme.colors.background,
            titleContentColor = if (isDarkTheme) CinelexTheme.colors.textPrimary else CinelexTheme.colors.primary,
            actionIconContentColor = CinelexTheme.colors.textPrimary,
        ),
        actions = {
            IconButton(onClick = onLanguageClick) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = stringResource(R.string.action_language),
                )
            }
            IconButton(onClick = onThemeClick) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = stringResource(R.string.action_theme),
                )
            }
        },
    )
}

@Preview(name = "Light")
@Composable
private fun CinelexAppBarLightPreview() {
    CinelexTheme(darkTheme = false) {
        CinelexAppBar(
            isDarkTheme = false,
            onLanguageClick = {},
            onThemeClick = {},
        )
    }
}

@Preview(name = "Dark")
@Composable
private fun CinelexAppBarDarkPreview() {
    CinelexTheme(darkTheme = true) {
        CinelexAppBar(
            isDarkTheme = true,
            onLanguageClick = {},
            onThemeClick = {},
        )
    }
}
