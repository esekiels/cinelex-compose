/*
 * Cinelex
 * CinelexAppBar
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

package co.esekiels.cinelex.core.design.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import co.esekiels.cinelex.core.design.R
import co.esekiels.cinelex.core.design.theme.CinelexTheme

@Composable
fun CinelexAppBar(
    title: String,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = CinelexTheme.typography.headingSmall,
                color = CinelexTheme.colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CinelexTheme.colors.background,
            actionIconContentColor = CinelexTheme.colors.textPrimary,
        ),
    )
}

@Preview(name = "Home Light")
@Composable
private fun CinelexAppBarHomeLightPreview() {
    CinelexTheme(darkTheme = false) {
        CinelexAppBar(
            title = "Cinelex",
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Language, contentDescription = null)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.DarkMode, contentDescription = null)
                }
            },
        )
    }
}

@Preview(name = "Details")
@Composable
private fun CinelexAppBarDetailsPreview() {
    CinelexTheme(darkTheme = false) {
        CinelexAppBar(
            title = "The Shawshank Redemption",
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            },
        )
    }
}
