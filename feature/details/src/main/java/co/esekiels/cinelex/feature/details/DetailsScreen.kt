/*
 * Cinelex
 * DetailsScreen
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.esekiels.cinelex.core.design.component.CinelexAppBar
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.navigation.LocalComposeNavigator
import co.esekiels.cinelex.feature.details.component.DetailsContent
import co.esekiels.cinelex.feature.details.component.DetailsSkeleton

@Composable
fun DetailsScreen(
    movieId: Int,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val navigator = LocalComposeNavigator.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val movie by viewModel.movieDetails.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.loadDetails(movieId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CinelexAppBar(
            title = movie?.title.orEmpty(),
            navigationIcon = {
                IconButton(onClick = { navigator.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = CinelexTheme.colors.textPrimary,
                    )
                }
            },
        )

        movie?.let { DetailsContent(it) } ?: DetailsSkeleton()
    }

    if (uiState is DetailsUiState.Error) {
        val error = uiState as DetailsUiState.Error
        AlertDialog(
            onDismissRequest = viewModel::dismissError,
            containerColor = CinelexTheme.colors.background,
            titleContentColor = CinelexTheme.colors.textPrimary,
            textContentColor = CinelexTheme.colors.textSecondary,
            title = { Text("Error") },
            text = { Text(error.message ?: "Unknown error") },
            confirmButton = {
                TextButton(onClick = viewModel::dismissError) {
                    Text("OK", color = CinelexTheme.colors.primary)
                }
            },
        )
    }
}
