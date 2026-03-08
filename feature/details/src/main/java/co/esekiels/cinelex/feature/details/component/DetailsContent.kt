/*
 * Cinelex
 * DetailsContent
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.feature.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.model.MovieDetails
import coil3.compose.AsyncImage

@Composable
internal fun DetailsContent(movie: MovieDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Backdrop(movie)
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Info(movie)
            Overview(movie)
            Rating(movie)
            CastSection(movie)
            CrewSection(movie)
            TrailerSection(movie)
        }
    }
}

@Composable
private fun Backdrop(movie: MovieDetails) {
    AsyncImage(
        model = movie.backdropUrl,
        contentDescription = movie.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
    )
}

@Composable
private fun Info(movie: MovieDetails) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = movie.title,
            style = CinelexTheme.typography.headingMedium,
            color = CinelexTheme.colors.textPrimary,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = movie.genreFormatted,
                style = CinelexTheme.typography.bodySmall,
                color = CinelexTheme.colors.textSecondary,
            )
            Text(
                text = "·",
                style = CinelexTheme.typography.bodySmall,
                color = CinelexTheme.colors.textSecondary,
            )
            Text(
                text = movie.releaseYearFormatted,
                style = CinelexTheme.typography.bodySmall,
                color = CinelexTheme.colors.textSecondary,
            )
            Text(
                text = "·",
                style = CinelexTheme.typography.bodySmall,
                color = CinelexTheme.colors.textSecondary,
            )
            Text(
                text = movie.durationFormatted,
                style = CinelexTheme.typography.bodySmall,
                color = CinelexTheme.colors.textSecondary,
            )
        }
    }
}

@Composable
private fun Overview(movie: MovieDetails) {
    Text(
        text = movie.overview,
        style = CinelexTheme.typography.bodyLarge,
        color = CinelexTheme.colors.textPrimary,
    )
}

@Composable
private fun Rating(movie: MovieDetails) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            repeat(10) { index ->
                val filled = movie.isStarFilled(index)
                Icon(
                    imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "Rating star",
                    tint = if (filled) Color(0xFFFFD700) else Color.Gray,
                    modifier = Modifier.size(14.dp),
                )
            }
        }
        Text(
            text = movie.scoreRating,
            style = CinelexTheme.typography.bodySmall,
            color = CinelexTheme.colors.textSecondary,
        )
    }
}

@Preview(name = "Light")
@Composable
private fun DetailsContentLightPreview() {
    CinelexTheme(darkTheme = false) {
        Column(modifier = Modifier.background(CinelexTheme.colors.background)) {
            DetailsContent(MovieDetails.stub)
        }
    }
}

@Preview(name = "Dark")
@Composable
private fun DetailsContentDarkPreview() {
    CinelexTheme(darkTheme = true) {
        Column(modifier = Modifier.background(CinelexTheme.colors.background)) {
            DetailsContent(MovieDetails.stub)
        }
    }
}
