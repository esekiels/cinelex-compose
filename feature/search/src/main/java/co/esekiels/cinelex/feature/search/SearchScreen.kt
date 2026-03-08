/*
 * Cinelex
 * SearchScreen
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.esekiels.cinelex.core.design.R
import co.esekiels.cinelex.core.design.component.CinelexAppBar
import co.esekiels.cinelex.core.design.component.CinelexSearchBar
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.model.Movie
import co.esekiels.cinelex.core.navigation.CinelexRoute
import co.esekiels.cinelex.core.navigation.LocalComposeNavigator
import co.esekiels.cinelex.feature.search.component.MovieList
import co.esekiels.cinelex.feature.search.component.RecommendationList

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
	val navigator = LocalComposeNavigator.current
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val query by viewModel.query.collectAsStateWithLifecycle()
	val movies by viewModel.movies.collectAsStateWithLifecycle()
	val recommendations by viewModel.recommendations.collectAsStateWithLifecycle()
	val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()

	SearchContent(
		uiState = uiState,
		query = query,
		movies = movies,
		recommendations = recommendations,
		isLoadingMore = isLoadingMore,
		onQueryChanged = viewModel::onQueryChanged,
		onMovieClick = { navigator.navigate(CinelexRoute.Details(it.id)) },
		onLoadMore = viewModel::loadMore,
	)
}

@Composable
private fun SearchContent(
	uiState: SearchUiState,
	query: String,
	movies: List<Movie>,
	recommendations: List<Movie>,
	isLoadingMore: Boolean,
	onQueryChanged: (String) -> Unit,
	onMovieClick: (Movie) -> Unit,
	onLoadMore: () -> Unit,
) {
	Scaffold(
		containerColor = CinelexTheme.colors.background,
		topBar = { CinelexAppBar(title = stringResource(R.string.search)) },
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
		) {
			CinelexSearchBar(
				query = query,
				onQueryChanged = onQueryChanged,
				placeholder = stringResource(R.string.search_prompt),
			)

			when {
				uiState is SearchUiState.Loading -> LoadingContent()
				query.isBlank() -> RecommendationList(recommendations, onMovieClick)
				movies.isEmpty() -> EmptyContent(query)
				else -> MovieList(movies, isLoadingMore, onMovieClick, onLoadMore)
			}
		}
	}
}

@Composable
private fun LoadingContent() {
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		CircularProgressIndicator(color = CinelexTheme.colors.primary)
	}
}

@Composable
private fun EmptyContent(query: String) {
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Text(
			text = stringResource(R.string.no_results, query),
			style = CinelexTheme.typography.bodyLarge,
			color = CinelexTheme.colors.textSecondary,
		)
	}
}
