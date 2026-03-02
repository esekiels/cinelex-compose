package co.esekiels.cinelex.core.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.esekiels.cinelex.core.design.theme.CinelexTheme


/*
 * Cinelex
 * CinelexLoading
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

@Composable
fun BoxScope.CinelexLoading() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
        color = CinelexTheme.colors.primary
    )
}

@Preview
@Composable
private fun CinelexLoadingPreview() {
    CinelexTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CinelexLoading()
        }
    }
}
