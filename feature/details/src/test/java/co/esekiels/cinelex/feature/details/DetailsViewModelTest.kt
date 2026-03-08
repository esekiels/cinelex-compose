/*
 * Cinelex
 * DetailsViewModelTest
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.feature.details

import co.esekiels.cinelex.core.data.movie.MovieRepository
import co.esekiels.cinelex.core.testing.MainCoroutinesRule
import co.esekiels.cinelex.core.testing.MovieDetailsStub
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel
    private val movieRepository: MovieRepository = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Test
    fun shouldFetchMovieDetails() = runTest {
        val stub = MovieDetailsStub
        whenever(movieRepository.fetchMovieDetails(stub.id)).thenReturn(stub)

        viewModel = DetailsViewModel(movieRepository)
        viewModel.loadDetails(stub.id)

        assertNotNull(viewModel.movieDetails.value)
        assertEquals(stub.id, viewModel.movieDetails.value!!.id)
        assertEquals(stub.title, viewModel.movieDetails.value!!.title)
        assertEquals(DetailsUiState.Idle, viewModel.uiState.value)

        verify(movieRepository).fetchMovieDetails(stub.id)
    }
}
