package co.esekiels.cinelex

import android.content.res.Configuration as AndroidConfig
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.esekiels.cinelex.core.model.UiTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val darkTheme = when (uiState.uiTheme) {
                UiTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
                UiTheme.DARK -> true
                UiTheme.LIGHT -> false
            }

            val locale = Locale.forLanguageTag(uiState.language)
            val configuration = AndroidConfig(LocalConfiguration.current).apply {
                setLocale(locale)
            }
            val localizedContext = createConfigurationContext(configuration)

            CompositionLocalProvider(LocalContext provides localizedContext) {
                CinelexMain(
                    darkTheme = darkTheme,
                    currentLanguage = uiState.language,
                    currentUiTheme = uiState.uiTheme,
                    onLanguageSelected = viewModel::setLanguage,
                    onThemeSelected = viewModel::setUiTheme,
                )
            }
        }
    }
}
