/*
 * Cinelex
 * MoviePosterCarousel
 *
 * Created by Esekiel Surbakti on 05/03/26
 */

package co.esekiels.cinelex.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.model.Movie
import coil3.compose.AsyncImage

@Composable
fun MoviePosterCarousel(
	title: String,
	movies: List<Movie>,
	onMovieClick: (Movie) -> Unit,
) {
	val screenWidth = with(LocalDensity.current) {
		LocalWindowInfo.current.containerSize.width.toDp()
	}
	val cardWidth = screenWidth * 0.4f

	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {
		Text(
			text = title,
			style = CinelexTheme.typography.headingSmall,
			color = CinelexTheme.colors.textPrimary,
			modifier = Modifier.padding(horizontal = 16.dp),
		)
		LazyRow(
			contentPadding = PaddingValues(horizontal = 16.dp),
			horizontalArrangement = Arrangement.spacedBy(16.dp),
		) {
			items(movies, key = { it.id }) { movie ->
				AsyncImage(
					model = movie.posterUrl,
					contentDescription = movie.title,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.width(cardWidth)
						.aspectRatio(2f / 3f)
						.clip(RoundedCornerShape(8.dp))
						.clickable { onMovieClick(movie) },
				)
			}
		}
	}
}
