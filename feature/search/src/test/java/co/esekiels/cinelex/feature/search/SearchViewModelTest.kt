/*
 * Cinelex
 * SearchViewModelTest
 *
 * Created by Esekiel Surbakti on 09/03/26
 */

package co.esekiels.cinelex.feature.search

import co.esekiels.cinelex.core.data.genre.GenreRepository
import co.esekiels.cinelex.core.data.movie.MovieRepository
import co.esekiels.cinelex.core.data.movie.SearchResult
import co.esekiels.cinelex.core.testing.GenreStubs
import co.esekiels.cinelex.core.testing.MainCoroutinesRule
import co.esekiels.cinelex.core.testing.MovieStubs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchViewModelTest {

	private lateinit var viewModel: SearchViewModel
	private val movieRepository: MovieRepository = mock()
	private val genreRepository: GenreRepository = mock()

	@get:Rule
	val coroutinesRule = MainCoroutinesRule()

	@Before
	fun setup() {
		whenever(genreRepository.fetchGenres()).thenReturn(flowOf(GenreStubs))
		whenever(movieRepository.fetchPopular()).thenReturn(flowOf(MovieStubs))
	}

	@Test
	fun shouldLoadRecommendationsOnInit() = runTest {
		viewModel = SearchViewModel(movieRepository, genreRepository)

		val values = mutableListOf<List<co.esekiels.cinelex.core.model.Movie>>()
		backgroundScope.launch(coroutinesRule.testDispatcher) {
			viewModel.recommendations.collect { values.add(it) }
		}
		advanceUntilIdle()

		assertEquals(MovieStubs.size, values.last().size)
		assertEquals(SearchUiState.Idle, viewModel.uiState.value)

		verify(genreRepository, atLeastOnce()).fetchGenres()
		verify(movieRepository, atLeastOnce()).fetchPopular()
	}

	@Test
	fun shouldSearchMovies() = runTest {
		val searchResult = SearchResult(movies = listOf(MovieStubs.first()), totalPages = 1)
		whenever(movieRepository.searchMovies("shawshank", 1)).thenReturn(searchResult)

		viewModel = SearchViewModel(movieRepository, genreRepository)
		advanceUntilIdle()

		viewModel.onQueryChanged("shawshank")
		advanceTimeBy(501)
		advanceUntilIdle()

		assertEquals(1, viewModel.movies.value.size)
		assertEquals(SearchUiState.Idle, viewModel.uiState.value)

		verify(movieRepository).searchMovies("shawshank", 1)
	}

	@Test
	fun shouldSetErrorStateOnSearchFailure() = runTest {
		whenever(movieRepository.searchMovies("error", 1)).thenThrow(RuntimeException("Network error"))

		viewModel = SearchViewModel(movieRepository, genreRepository)
		advanceUntilIdle()

		viewModel.onQueryChanged("error")
		advanceTimeBy(501)
		advanceUntilIdle()

		assertTrue(viewModel.uiState.value is SearchUiState.Error)
	}
}
