package co.esekiels.cinelex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.esekiels.cinelex.core.data.user.UserDataRepository
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.UiTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    val uiState: StateFlow<MainUiState> = userDataRepository.userPreferences
        .map { prefs ->
            MainUiState(
                language = prefs.language,
                uiTheme = prefs.uiTheme,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = MainUiState(),
        )

    fun setUiTheme(uiTheme: UiTheme) {
        viewModelScope.launch {
            userDataRepository.setUiTheme(uiTheme)
        }
    }

    fun setLanguage(language: Language) {
        viewModelScope.launch {
            userDataRepository.setLanguage(language)
        }
    }
}

private const val STOP_TIMEOUT_MILLIS = 5_000L

data class MainUiState(
    val language: String = Language.ENGLISH.code,
    val uiTheme: UiTheme = UiTheme.FOLLOW_SYSTEM,
)
