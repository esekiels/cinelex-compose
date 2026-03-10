/*
 * Cinelex
 * HomeViewModel
 *
 * Created by Esekiel Surbakti on 05/03/26
 */

package co.esekiels.cinelex.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.esekiels.cinelex.core.data.movie.MovieRepository
import co.esekiels.cinelex.core.data.user.UserDataRepository
import co.esekiels.cinelex.core.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> get() = _uiState.asStateFlow()

    val nowPlayingState: StateFlow<List<Movie>> = movieRepository.fetchNowPlaying()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val upcomingState: StateFlow<List<Movie>> = movieRepository.fetchUpcoming()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val topRatedState: StateFlow<List<Movie>> = movieRepository.fetchTopRated()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val popularState: StateFlow<List<Movie>> = movieRepository.fetchPopular()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            val cached = movieRepository.fetchNowPlaying().first()
            if (cached.isEmpty()) {
                refreshAll()
            } else {
                _uiState.value = HomeUiState.Idle
            }
        }

        userDataRepository.observeLanguage()
            .drop(1)
            .onEach { refreshAll() }
            .launchIn(viewModelScope)

        combine(nowPlayingState, upcomingState, topRatedState, popularState) { np, up, tr, pop ->
            np.isNotEmpty() || up.isNotEmpty() || tr.isNotEmpty() || pop.isNotEmpty()
        }.onEach { hasData ->
            if (hasData && _uiState.value is HomeUiState.Loading) {
                _uiState.value = HomeUiState.Idle
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun refreshAll() {
        _uiState.value = HomeUiState.Loading
        try {
            movieRepository.refreshMovies()
            _uiState.value = HomeUiState.Idle
        } catch (e: Exception) {
            _uiState.value = HomeUiState.Error(e.message)
        }
    }

    fun refresh() {
        viewModelScope.launch { refreshAll() }
    }

    fun dismissError() {
        _uiState.value = HomeUiState.Idle
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Idle : HomeUiState
    data class Error(val message: String?) : HomeUiState
}
