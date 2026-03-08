/*
 * Cinelex
 * DetailsSkeleton
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.feature.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.esekiels.cinelex.core.design.component.ShimmerBox
import co.esekiels.cinelex.core.design.theme.CinelexTheme

@Composable
internal fun DetailsSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
        )
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Title
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ShimmerBox(modifier = Modifier.width(200.dp).height(24.dp))
                ShimmerBox(modifier = Modifier.width(260.dp).height(16.dp))
            }
            // Overview
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ShimmerBox(modifier = Modifier.fillMaxWidth().height(14.dp))
                ShimmerBox(modifier = Modifier.fillMaxWidth().height(14.dp))
                ShimmerBox(modifier = Modifier.fillMaxWidth(0.7f).height(14.dp))
            }
            // Rating
            ShimmerBox(modifier = Modifier.width(180.dp).height(14.dp))
            // Cast
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ShimmerBox(modifier = Modifier.width(60.dp).height(20.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(5) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.width(80.dp),
                        ) {
                            ShimmerBox(modifier = Modifier.size(56.dp).clip(CircleShape))
                            ShimmerBox(modifier = Modifier.width(60.dp).height(12.dp))
                            ShimmerBox(modifier = Modifier.width(50.dp).height(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Loading")
@Composable
private fun DetailsSkeletonPreview() {
    CinelexTheme(darkTheme = false) {
        Column(modifier = Modifier.background(CinelexTheme.colors.background)) {
            DetailsSkeleton()
        }
    }
}
