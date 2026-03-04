/*
 * Cinelex
 * HomeScreen
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

package co.esekiels.cinelex.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.esekiels.cinelex.core.design.R
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.esekiels.cinelex.core.design.component.CinelexAppBar
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.model.Movie
import co.esekiels.cinelex.feature.home.component.MovieBackdropCarousel
import co.esekiels.cinelex.feature.home.component.MoviePosterCarousel
import co.esekiels.cinelex.feature.home.component.ShimmerBox

@Composable
fun HomeScreen(
	isDarkTheme: Boolean = false,
	onLanguageClick: () -> Unit = {},
	onThemeClick: () -> Unit = {},
	onMovieClick: (Movie) -> Unit = {},
	viewModel: HomeViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val nowPlaying by viewModel.nowPlayingState.collectAsStateWithLifecycle()
	val upcoming by viewModel.upcomingState.collectAsStateWithLifecycle()
	val topRated by viewModel.topRatedState.collectAsStateWithLifecycle()
	val popular by viewModel.popularState.collectAsStateWithLifecycle()

	val isLoading = uiState is HomeUiState.Loading

	Column(modifier = Modifier.fillMaxSize()) {
		CinelexAppBar(
			isDarkTheme = isDarkTheme,
			onLanguageClick = onLanguageClick,
			onThemeClick = onThemeClick,
		)
		PullToRefreshBox(
			isRefreshing = isLoading,
			onRefresh = viewModel::refresh,
			modifier = Modifier.fillMaxSize(),
		) {
			LazyColumn(
				contentPadding = PaddingValues(bottom = 24.dp),
				verticalArrangement = Arrangement.spacedBy(24.dp),
				modifier = Modifier.fillMaxSize(),
			) {
				item {
					MovieSection(
						title = stringResource(R.string.now_playing),
						movies = nowPlaying,
						style = CarouselStyle.POSTER,
						isLoading = isLoading,
						onMovieClick = onMovieClick,
					)
				}
				item {
					MovieSection(
						title = stringResource(R.string.upcoming),
						movies = upcoming,
						style = CarouselStyle.BACKDROP,
						isLoading = isLoading,
						onMovieClick = onMovieClick,
					)
				}
				item {
					MovieSection(
						title = stringResource(R.string.top_rated),
						movies = topRated,
						style = CarouselStyle.BACKDROP,
						isLoading = isLoading,
						onMovieClick = onMovieClick,
					)
				}
				item {
					MovieSection(
						title = stringResource(R.string.popular),
						movies = popular,
						style = CarouselStyle.BACKDROP,
						isLoading = isLoading,
						onMovieClick = onMovieClick,
					)
				}
			}
		}
	}

	if (uiState is HomeUiState.Error) {
		val error = uiState as HomeUiState.Error
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

@Composable
private fun MovieSection(
	title: String,
	movies: List<Movie>,
	style: CarouselStyle,
	isLoading: Boolean,
	onMovieClick: (Movie) -> Unit,
) {
	if (movies.isNotEmpty()) {
		when (style) {
			CarouselStyle.POSTER -> MoviePosterCarousel(title, movies, onMovieClick)
			CarouselStyle.BACKDROP -> MovieBackdropCarousel(title, movies, onMovieClick)
		}
	} else {
		SectionSkeleton(style)
	}
}

@Composable
private fun SectionSkeleton(style: CarouselStyle) {
	val screenWidth = with(LocalDensity.current) {
		LocalWindowInfo.current.containerSize.width.toDp()
	}
	val (cardWidth, aspectRatio, spacing) = when (style) {
		CarouselStyle.POSTER -> Triple(screenWidth * 0.4f, 2f / 3f, 16.dp)
		CarouselStyle.BACKDROP -> Triple(screenWidth * 0.75f, 16f / 9f, 12.dp)
	}

	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {
		ShimmerBox(
			modifier = Modifier
				.padding(horizontal = 16.dp)
				.width(120.dp)
				.height(24.dp),
		)
		LazyRow(
			contentPadding = PaddingValues(horizontal = 16.dp),
			horizontalArrangement = Arrangement.spacedBy(spacing),
			userScrollEnabled = false,
		) {
			items(5) {
				ShimmerBox(
					modifier = Modifier
						.width(cardWidth)
						.aspectRatio(aspectRatio),
				)
			}
		}
	}
}

private enum class CarouselStyle { POSTER, BACKDROP }
