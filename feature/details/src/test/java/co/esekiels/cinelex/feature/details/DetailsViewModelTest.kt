/*
 * Cinelex
 * DetailsViewModelTest
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.feature.details

import co.esekiels.cinelex.core.data.details.DetailsRepository
import co.esekiels.cinelex.core.model.MovieDetails
import co.esekiels.cinelex.core.testing.MainCoroutinesRule
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
    private val detailsRepository: DetailsRepository = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Test
    fun shouldFetchMovieDetails() = runTest {
        val stub = MovieDetails.stub
        whenever(detailsRepository.fetchMovieDetails(stub.id)).thenReturn(stub)

        viewModel = DetailsViewModel(detailsRepository)
        viewModel.loadDetails(stub.id)

        assertNotNull(viewModel.movieDetails.value)
        assertEquals(stub.id, viewModel.movieDetails.value!!.id)
        assertEquals(stub.title, viewModel.movieDetails.value!!.title)
        assertEquals(DetailsUiState.Idle, viewModel.uiState.value)

        verify(detailsRepository).fetchMovieDetails(stub.id)
    }
}
