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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.esekiels.cinelex.core.design.R
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import co.esekiels.cinelex.core.design.component.CinelexAppBar
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.model.Movie
import co.esekiels.cinelex.core.navigation.CinelexRoute
import co.esekiels.cinelex.core.navigation.LocalComposeNavigator
import co.esekiels.cinelex.core.preview.MovieStubs
import co.esekiels.cinelex.feature.home.component.MovieBackdropCarousel
import co.esekiels.cinelex.feature.home.component.MoviePosterCarousel
import co.esekiels.cinelex.core.design.component.ShimmerBox

@Composable
fun HomeScreen(
    isDarkTheme: Boolean = false,
    onLanguageClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val navigator = LocalComposeNavigator.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val nowPlaying by viewModel.nowPlayingState.collectAsStateWithLifecycle()
    val upcoming by viewModel.upcomingState.collectAsStateWithLifecycle()
    val topRated by viewModel.topRatedState.collectAsStateWithLifecycle()
    val popular by viewModel.popularState.collectAsStateWithLifecycle()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState != HomeUiState.Loading) isRefreshing = false
    }

    HomeContent(
        isDarkTheme = isDarkTheme,
        isRefreshing = isRefreshing,
        nowPlaying = nowPlaying,
        upcoming = upcoming,
        topRated = topRated,
        popular = popular,
        onLanguageClick = onLanguageClick,
        onThemeClick = onThemeClick,
        onMovieClick = { movie -> navigator.navigate(CinelexRoute.Details(movie.id)) },
        onRefresh = {
            isRefreshing = true
            viewModel.refresh()
        },
    )

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
private fun HomeContent(
    isDarkTheme: Boolean,
    isRefreshing: Boolean,
    nowPlaying: List<Movie>,
    upcoming: List<Movie>,
    topRated: List<Movie>,
    popular: List<Movie>,
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
    onMovieClick: (Movie) -> Unit,
    onRefresh: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeAppBar(isDarkTheme, onLanguageClick, onThemeClick)
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
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
                        onMovieClick = onMovieClick,
                    )
                }
                item {
                    MovieSection(
                        title = stringResource(R.string.upcoming),
                        movies = upcoming,
                        style = CarouselStyle.BACKDROP,
                        onMovieClick = onMovieClick,
                    )
                }
                item {
                    MovieSection(
                        title = stringResource(R.string.top_rated),
                        movies = topRated,
                        style = CarouselStyle.BACKDROP,
                        onMovieClick = onMovieClick,
                    )
                }
                item {
                    MovieSection(
                        title = stringResource(R.string.popular),
                        movies = popular,
                        style = CarouselStyle.BACKDROP,
                        onMovieClick = onMovieClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeAppBar(
    isDarkTheme: Boolean,
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
) {
    CinelexAppBar(
        title = stringResource(R.string.app_name),
        actions = {
            IconButton(onClick = onLanguageClick) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = stringResource(R.string.action_language),
                )
            }
            IconButton(onClick = onThemeClick) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = stringResource(R.string.action_theme),
                )
            }
        },
    )
}

@Composable
private fun MovieSection(
    title: String,
    movies: List<Movie>,
    style: CarouselStyle,
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

@Preview(name = "Light")
@Composable
private fun HomeScreenLightPreview() {
    CinelexTheme(darkTheme = false) {
        HomeContent(
            isDarkTheme = false,
            isRefreshing = false,
            nowPlaying = MovieStubs,
            upcoming = MovieStubs,
            topRated = MovieStubs,
            popular = MovieStubs,
            onLanguageClick = {},
            onThemeClick = {},
            onMovieClick = {},
            onRefresh = {},
        )
    }
}

@Preview(name = "Dark")
@Composable
private fun HomeScreenDarkPreview() {
    CinelexTheme(darkTheme = true) {
        HomeContent(
            isDarkTheme = true,
            isRefreshing = false,
            nowPlaying = MovieStubs,
            upcoming = MovieStubs,
            topRated = MovieStubs,
            popular = MovieStubs,
            onLanguageClick = {},
            onThemeClick = {},
            onMovieClick = {},
            onRefresh = {},
        )
    }
}

@Preview(name = "Loading")
@Composable
private fun HomeScreenLoadingPreview() {
    CinelexTheme(darkTheme = false) {
        HomeContent(
            isDarkTheme = false,
            isRefreshing = true,
            nowPlaying = emptyList(),
            upcoming = emptyList(),
            topRated = emptyList(),
            popular = emptyList(),
            onLanguageClick = {},
            onThemeClick = {},
            onMovieClick = {},
            onRefresh = {},
        )
    }
}
