/*
 * Cinelex
 * DetailsSections
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.feature.details.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.esekiels.cinelex.core.design.R
import co.esekiels.cinelex.core.design.theme.CinelexTheme
import co.esekiels.cinelex.core.model.Cast
import co.esekiels.cinelex.core.model.Crew
import co.esekiels.cinelex.core.model.MovieDetails
import co.esekiels.cinelex.core.model.Video
import coil3.compose.AsyncImage
import androidx.core.net.toUri

@Composable
internal fun CastSection(movie: MovieDetails) {
    val cast = movie.cast
    if (cast.isNullOrEmpty()) return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.cast),
            style = CinelexTheme.typography.headingSmall,
            color = CinelexTheme.colors.textPrimary,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 16.dp),
        ) {
            items(
                items = cast.take(10),
                key = { it.id },
            ) { member ->
                CastCard(member)
            }
        }
    }
}

@Composable
private fun CastCard(member: Cast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.width(80.dp),
    ) {
        if (member.profileUrl != null) {
            AsyncImage(
                model = member.profileUrl,
                contentDescription = member.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
            )
        } else {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(CinelexTheme.colors.textSecondary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = CinelexTheme.colors.textSecondary,
                    modifier = Modifier.size(32.dp),
                )
            }
        }
        Text(
            text = member.name,
            style = CinelexTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = CinelexTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = member.character,
            style = CinelexTheme.typography.labelSmall,
            color = CinelexTheme.colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun CrewSection(movie: MovieDetails) {
    val directors = movie.directors.orEmpty()
    val producers = movie.producers.orEmpty()
    val writers = movie.screenwriters.orEmpty()

    if (directors.isEmpty() && producers.isEmpty() && writers.isEmpty()) return

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.crew),
            style = CinelexTheme.typography.headingSmall,
            color = CinelexTheme.colors.textPrimary,
        )
        CrewRow(stringResource(R.string.director), directors)
        CrewRow(stringResource(R.string.producer), producers)
        CrewRow(stringResource(R.string.writer), writers)
    }
}

@Composable
private fun CrewRow(title: String, crew: List<Crew>) {
    if (crew.isEmpty()) return

    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = title,
            style = CinelexTheme.typography.bodySmall,
            color = CinelexTheme.colors.textSecondary,
        )
        Text(
            text = crew.joinToString(", ") { it.name },
            style = CinelexTheme.typography.bodySmall,
            color = CinelexTheme.colors.textPrimary,
        )
    }
}

@Composable
internal fun TrailerSection(movie: MovieDetails) {
    val trailers = movie.youtubeTrailers
    if (trailers.isNullOrEmpty()) return

    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.trailers),
            style = CinelexTheme.typography.headingSmall,
            color = CinelexTheme.colors.textPrimary,
        )
        trailers.forEach { trailer ->
            TrailerRow(
                trailer = trailer,
                onClick = {
                    trailer.youtubeUrl?.let { url ->
                        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                    }
                },
            )
        }
    }
}

@Composable
private fun TrailerRow(
    trailer: Video,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.PlayCircle,
            contentDescription = "Play trailer",
            tint = CinelexTheme.colors.primary,
            modifier = Modifier.size(28.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = trailer.name,
            style = CinelexTheme.typography.bodyMedium,
            color = CinelexTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
