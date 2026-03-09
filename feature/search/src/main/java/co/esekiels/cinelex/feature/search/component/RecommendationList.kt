/*
 * Cinelex
 * RecommendationList
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.feature.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.esekiels.cinelex.core.design.R
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.model.Movie
import coil3.compose.AsyncImage

@Composable
internal fun RecommendationList(
	movies: List<Movie>,
	onMovieClick: (Movie) -> Unit,
) {
	LazyColumn(contentPadding = PaddingValues(bottom = 24.dp)) {
		item {
			Text(
				text = stringResource(R.string.recommendations),
				style = CinelexTheme.typography.headingSmall,
				color = CinelexTheme.colors.textPrimary,
				modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
			)
		}
		items(movies, key = { it.id }) { movie ->
			RecommendationRow(movie = movie, onClick = { onMovieClick(movie) })
		}
	}
}

@Composable
private fun RecommendationRow(movie: Movie, onClick: () -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable(onClick = onClick)
			.padding(horizontal = 16.dp, vertical = 8.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	) {
		AsyncImage(
			model = movie.backdropUrl,
			contentDescription = movie.title,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.width(120.dp)
				.aspectRatio(16f / 9f)
				.clip(RoundedCornerShape(8.dp)),
		)
		Text(
			text = movie.title,
			style = CinelexTheme.typography.bodyMedium,
			color = CinelexTheme.colors.textPrimary,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			modifier = Modifier.weight(1f),
		)
		Icon(
			imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
			contentDescription = null,
			tint = CinelexTheme.colors.textSecondary,
		)
	}
}
