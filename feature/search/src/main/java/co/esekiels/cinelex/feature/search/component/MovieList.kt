/*
 * Cinelex
 * MovieList
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.feature.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.model.Genre
import co.esekiels.cinelex.core.model.Movie
import coil3.compose.AsyncImage

@Composable
internal fun MovieList(
	movies: List<Movie>,
	isLoadingMore: Boolean,
	onMovieClick: (Movie) -> Unit,
	onLoadMore: () -> Unit,
) {
	val listState = rememberLazyListState()
	val shouldLoadMore by remember {
		derivedStateOf {
			val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
			lastVisible >= listState.layoutInfo.totalItemsCount - 3
		}
	}

	LaunchedEffect(shouldLoadMore) {
		if (shouldLoadMore) onLoadMore()
	}

	LazyColumn(
		state = listState,
		contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp),
	) {
		items(movies, key = { it.id }) { movie ->
			MovieRow(movie = movie, onClick = { onMovieClick(movie) })
		}
		if (isLoadingMore) {
			item {
				Box(
					modifier = Modifier.fillMaxWidth().padding(16.dp),
					contentAlignment = Alignment.Center,
				) {
					CircularProgressIndicator(color = CinelexTheme.colors.primary)
				}
			}
		}
	}
}

@Composable
private fun MovieRow(movie: Movie, onClick: () -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable(onClick = onClick),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	) {
		AsyncImage(
			model = movie.posterUrl,
			contentDescription = movie.title,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.width(48.dp)
				.aspectRatio(2f / 3f)
				.clip(RoundedCornerShape(8.dp)),
		)
		MovieInfo(movie = movie, modifier = Modifier.weight(1f))
		Icon(
			imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
			contentDescription = null,
			tint = CinelexTheme.colors.textSecondary,
		)
	}
}

@Composable
private fun MovieInfo(movie: Movie, modifier: Modifier = Modifier) {
	Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
		Text(
			text = movie.title,
			style = CinelexTheme.typography.bodyLarge,
			color = CinelexTheme.colors.textPrimary,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
		)
		Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			Text(
				text = movie.releaseDate.take(4).ifEmpty { "n/a" },
				style = CinelexTheme.typography.bodySmall,
				color = CinelexTheme.colors.textSecondary,
			)
			Text(
				text = "•",
				style = CinelexTheme.typography.bodySmall,
				color = CinelexTheme.colors.textSecondary,
			)
			Rating(movie)
		}
		GenreTags(movie.genres)
	}
}

@Composable
private fun Rating(movie: Movie) {
	Row(
		horizontalArrangement = Arrangement.spacedBy(4.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Icon(
			imageVector = Icons.Filled.Star,
			contentDescription = null,
			tint = Color(0xFFFFD700),
			modifier = Modifier.width(14.dp),
		)
		Text(
			text = movie.rating,
			style = CinelexTheme.typography.bodySmall,
			color = CinelexTheme.colors.textPrimary,
		)
		Text(
			text = "(${movie.voteCount})",
			style = CinelexTheme.typography.bodySmall,
			color = CinelexTheme.colors.textSecondary,
		)
	}
}

@Composable
private fun GenreTags(genres: List<Genre>?) {
	LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
		val items = genres?.takeIf { it.isNotEmpty() }
		if (items != null) {
			items(items, key = { it.id }) { genre ->
				GenreTag(name = genre.name, isPlaceholder = false)
			}
		} else {
			item { GenreTag(name = "n/a", isPlaceholder = true) }
		}
	}
}

@Composable
private fun GenreTag(name: String, isPlaceholder: Boolean) {
	Text(
		text = name,
		style = CinelexTheme.typography.bodySmall,
		color = if (isPlaceholder) CinelexTheme.colors.textSecondary else CinelexTheme.colors.textPrimary,
		modifier = Modifier
			.background(
				color = if (isPlaceholder) {
					CinelexTheme.colors.textSecondary.copy(alpha = 0.2f)
				} else {
					CinelexTheme.colors.primary.copy(alpha = 0.2f)
				},
				shape = CircleShape,
			)
			.padding(horizontal = 8.dp, vertical = 4.dp),
	)
}
