/*
 * Cinelex
 * HomeViewModel
 *
 * Created by Esekiel Surbakti on 05/03/26
 */

package co.esekiels.cinelex.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.esekiels.cinelex.core.data.home.HomeRepository
import co.esekiels.cinelex.core.data.user.UserDataRepository
import co.esekiels.cinelex.core.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> get() = _uiState.asStateFlow()

    private val _nowPlayingState = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlayingState: StateFlow<List<Movie>> get() = _nowPlayingState.asStateFlow()

    private val _upcomingState = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingState: StateFlow<List<Movie>> get() = _upcomingState.asStateFlow()

    private val _topRatedState = MutableStateFlow<List<Movie>>(emptyList())
    val topRatedState: StateFlow<List<Movie>> get() = _topRatedState.asStateFlow()

    private val _popularState = MutableStateFlow<List<Movie>>(emptyList())
    val popularState: StateFlow<List<Movie>> get() = _popularState.asStateFlow()

    init {
        userDataRepository.observeLanguage()
            .onEach { fetchAll() }
            .launchIn(viewModelScope)
    }

    private fun fetchAll() {
        _uiState.value = HomeUiState.Loading
        _nowPlayingState.value = emptyList()
        _upcomingState.value = emptyList()
        _topRatedState.value = emptyList()
        _popularState.value = emptyList()

        viewModelScope.launch {
            val nowPlaying = async { fetchSafely { homeRepository.fetchNowPlaying() } }
            val upcoming = async { fetchSafely { homeRepository.fetchUpcoming() } }
            val topRated = async { fetchSafely { homeRepository.fetchTopRated() } }
            val popular = async { fetchSafely { homeRepository.fetchPopular() } }

            _nowPlayingState.value = nowPlaying.await()
            _upcomingState.value = upcoming.await()
            _topRatedState.value = topRated.await()
            _popularState.value = popular.await()

            val hasData = _nowPlayingState.value.isNotEmpty() ||
                _upcomingState.value.isNotEmpty() ||
                _topRatedState.value.isNotEmpty() ||
                _popularState.value.isNotEmpty()

            if (_uiState.value is HomeUiState.Error && hasData) return@launch
            if (_uiState.value !is HomeUiState.Error) _uiState.value = HomeUiState.Idle
        }
    }

    private suspend fun fetchSafely(fetch: suspend () -> List<Movie>): List<Movie> {
        return try {
            fetch()
        } catch (e: Exception) {
            _uiState.value = HomeUiState.Error(e.message)
            emptyList()
        }
    }

    fun refresh() {
        fetchAll()
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
