/*
 * Cinelex
 * DetailsViewModel
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.esekiels.cinelex.core.data.movie.MovieRepository
import co.esekiels.cinelex.core.model.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> get() = _uiState.asStateFlow()

    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails: StateFlow<MovieDetails?> get() = _movieDetails.asStateFlow()

    private var movieId: Int = -1

    fun loadDetails(id: Int) {
        if (movieId == id) return
        movieId = id
        _uiState.value = DetailsUiState.Loading
        viewModelScope.launch {
            try {
                _movieDetails.value = movieRepository.fetchMovieDetails(movieId)
                _uiState.value = DetailsUiState.Idle
            } catch (e: Exception) {
                _uiState.value = DetailsUiState.Error(e.message)
            }
        }
    }

    fun dismissError() {
        _uiState.value = DetailsUiState.Idle
    }
}

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data object Idle : DetailsUiState
    data class Error(val message: String?) : DetailsUiState
}
