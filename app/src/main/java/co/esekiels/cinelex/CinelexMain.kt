package co.esekiels.cinelex

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import co.esekiels.cinelex.core.design.R
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.UiTheme
import co.esekiels.cinelex.core.navigation.CinelexNavigatorImpl
import co.esekiels.cinelex.core.navigation.CinelexRoute
import co.esekiels.cinelex.core.navigation.LocalComposeNavigator
import co.esekiels.cinelex.feature.details.DetailsScreen
import co.esekiels.cinelex.feature.home.HomeScreen

/*
 * Cinelex
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

@Composable
fun CinelexMain(
    darkTheme: Boolean,
    currentLanguage: String,
    currentUiTheme: UiTheme,
    onLanguageSelected: (Language) -> Unit,
    onThemeSelected: (UiTheme) -> Unit,
) {
    var showLanguagePicker by remember { mutableStateOf(false) }
    var showThemePicker by remember { mutableStateOf(false) }
    val backStack = rememberNavBackStack(CinelexRoute.Home)
    val navigator = remember(backStack) { CinelexNavigatorImpl(backStack) }

    CinelexTheme(darkTheme = darkTheme) {
        CompositionLocalProvider(
            LocalComposeNavigator provides navigator,
        ) {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                ),
                entryProvider = entryProvider<NavKey> {
                    entry<CinelexRoute.Home> {
                        HomeScreen(
                            isDarkTheme = darkTheme,
                            onLanguageClick = { showLanguagePicker = true },
                            onThemeClick = { showThemePicker = true },
                        )
                    }
                    entry<CinelexRoute.Details> { route ->
                        DetailsScreen(movieId = route.movieId)
                    }
                },
            )
        }

        if (showLanguagePicker) {
            LanguagePickerDialog(
                currentLanguage = Language.fromCode(currentLanguage),
                onLanguageSelected = { language ->
                    onLanguageSelected(language)
                    showLanguagePicker = false
                },
                onDismiss = { showLanguagePicker = false },
            )
        }

        if (showThemePicker) {
            ThemePickerDialog(
                currentTheme = currentUiTheme,
                onThemeSelected = { theme ->
                    onThemeSelected(theme)
                    showThemePicker = false
                },
                onDismiss = { showThemePicker = false },
            )
        }
    }
}

@Composable
private fun LanguagePickerDialog(
    currentLanguage: Language,
    onLanguageSelected: (Language) -> Unit,
    onDismiss: () -> Unit,
) {
    val options = listOf(
        Language.ENGLISH to stringResource(R.string.language_english),
        Language.INDONESIAN to stringResource(R.string.language_indonesian),
    )

    PickerDialog(
        title = stringResource(R.string.picker_language_title),
        options = options.map { it.second },
        selectedIndex = options.indexOfFirst { it.first == currentLanguage },
        onSelected = { index -> onLanguageSelected(options[index].first) },
        onDismiss = onDismiss,
    )
}

@Composable
private fun ThemePickerDialog(
    currentTheme: UiTheme,
    onThemeSelected: (UiTheme) -> Unit,
    onDismiss: () -> Unit,
) {
    val options = listOf(
        UiTheme.FOLLOW_SYSTEM to stringResource(R.string.theme_follow_system),
        UiTheme.DARK to stringResource(R.string.theme_dark),
        UiTheme.LIGHT to stringResource(R.string.theme_light),
    )

    PickerDialog(
        title = stringResource(R.string.picker_theme_title),
        options = options.map { it.second },
        selectedIndex = options.indexOfFirst { it.first == currentTheme },
        onSelected = { index -> onThemeSelected(options[index].first) },
        onDismiss = onDismiss,
    )
}

@Composable
private fun PickerDialog(
    title: String,
    options: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CinelexTheme.colors.background,
        title = {
            Text(
                text = title,
                style = CinelexTheme.typography.headingSmall,
                color = CinelexTheme.colors.textPrimary,
            )
        },
        text = {
            Column {
                options.forEachIndexed { index, label ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelected(index) }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = index == selectedIndex,
                            onClick = { onSelected(index) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = CinelexTheme.colors.primary,
                            ),
                        )
                        Text(
                            text = label,
                            style = CinelexTheme.typography.bodyLarge,
                            color = CinelexTheme.colors.textPrimary,
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "OK",
                    color = CinelexTheme.colors.primary,
                )
            }
        },
    )
}
