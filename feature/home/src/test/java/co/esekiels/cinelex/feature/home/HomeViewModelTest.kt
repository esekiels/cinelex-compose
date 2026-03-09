/*
 * Cinelex
 * HomeViewModelTest
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.feature.home

import co.esekiels.cinelex.core.data.movie.MovieRepository
import co.esekiels.cinelex.core.data.user.UserDataRepository
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.testing.MainCoroutinesRule
import co.esekiels.cinelex.core.testing.MovieStubs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val movieRepository: MovieRepository = mock()
    private val userDataRepository: UserDataRepository = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        whenever(userDataRepository.observeLanguage()).thenReturn(flowOf(Language.ENGLISH.tmdbCode))
        whenever(movieRepository.fetchNowPlaying()).thenReturn(flowOf(MovieStubs))
        whenever(movieRepository.fetchUpcoming()).thenReturn(flowOf(MovieStubs))
        whenever(movieRepository.fetchTopRated()).thenReturn(flowOf(MovieStubs))
        whenever(movieRepository.fetchPopular()).thenReturn(flowOf(MovieStubs))
    }

    @Test
    fun shouldLoadMoviesFromDatabase() = runTest {
        viewModel = HomeViewModel(movieRepository, userDataRepository)
        advanceUntilIdle()

        assertEquals(MovieStubs.size, viewModel.nowPlayingState.value.size)
        assertEquals(MovieStubs.size, viewModel.upcomingState.value.size)
        assertEquals(MovieStubs.size, viewModel.topRatedState.value.size)
        assertEquals(MovieStubs.size, viewModel.popularState.value.size)
        assertEquals(HomeUiState.Idle, viewModel.uiState.value)

        verify(movieRepository, atLeastOnce()).fetchNowPlaying()
        verify(movieRepository, atLeastOnce()).fetchUpcoming()
        verify(movieRepository, atLeastOnce()).fetchTopRated()
        verify(movieRepository, atLeastOnce()).fetchPopular()
    }
}
