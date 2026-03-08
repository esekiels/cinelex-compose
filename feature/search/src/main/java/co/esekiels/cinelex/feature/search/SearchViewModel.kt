/*
 * Cinelex
 * SearchViewModel
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.esekiels.cinelex.core.data.genre.GenreRepository
import co.esekiels.cinelex.core.data.movie.MovieRepository
import co.esekiels.cinelex.core.model.Genre
import co.esekiels.cinelex.core.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
	private val movieRepository: MovieRepository,
	private val genreRepository: GenreRepository,
) : ViewModel() {

	private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
	val uiState: StateFlow<SearchUiState> get() = _uiState.asStateFlow()

	private val _query = MutableStateFlow("")
	val query: StateFlow<String> get() = _query.asStateFlow()

	private val _movies = MutableStateFlow<List<Movie>>(emptyList())
	val movies: StateFlow<List<Movie>> get() = _movies.asStateFlow()

	private val _recommendations = MutableStateFlow<List<Movie>>(emptyList())
	val recommendations: StateFlow<List<Movie>> get() = _recommendations.asStateFlow()

	private val _isLoadingMore = MutableStateFlow(false)
	val isLoadingMore: StateFlow<Boolean> get() = _isLoadingMore.asStateFlow()

	private var genres: List<Genre> = emptyList()
	private var currentPage = 1
	private var totalPages = 1

	init {
		_query
			.debounce(DEBOUNCE_MS)
			.distinctUntilChanged()
			.filter { it.isNotBlank() }
			.onEach { search(it) }
			.launchIn(viewModelScope)

		loadRecommendations()
	}

	fun onQueryChanged(newQuery: String) {
		_query.value = newQuery
		if (newQuery.isBlank()) {
			_movies.value = emptyList()
			_uiState.value = SearchUiState.Idle
		}
	}

	fun loadRecommendations() {
		viewModelScope.launch {
			try {
				genres = genreRepository.fetchGenres()
				_recommendations.value = movieRepository.fetchPopular().mapGenres()
			} catch (_: Exception) { }
		}
	}

	private fun search(query: String) {
		currentPage = 1
		_uiState.value = SearchUiState.Loading
		viewModelScope.launch {
			try {
				val response = movieRepository.searchMovies(query, currentPage)
				totalPages = response.totalPages
				_movies.value = response.movies.mapGenres()
				_uiState.value = SearchUiState.Idle
			} catch (e: Exception) {
				_uiState.value = SearchUiState.Error(e.message)
			}
		}
	}

	fun loadMore() {
		if (_isLoadingMore.value || currentPage >= totalPages) return
		_isLoadingMore.value = true
		currentPage++
		viewModelScope.launch {
			try {
				val response = movieRepository.searchMovies(_query.value, currentPage)
				_movies.value = _movies.value + response.movies.mapGenres()
			} catch (_: Exception) {
				currentPage--
			}
			_isLoadingMore.value = false
		}
	}

	fun dismissError() {
		_uiState.value = SearchUiState.Idle
	}

	private fun List<Movie>.mapGenres(): List<Movie> = map { movie ->
		movie.copy(
			genres = movie.genreIds?.mapNotNull { id -> genres.find { it.id == id } }
		)
	}

	companion object {
		private const val DEBOUNCE_MS = 500L
	}
}

sealed interface SearchUiState {
	data object Idle : SearchUiState
	data object Loading : SearchUiState
	data class Error(val message: String?) : SearchUiState
}
