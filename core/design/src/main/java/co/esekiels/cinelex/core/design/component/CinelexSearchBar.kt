/*
 * Cinelex
 * CinelexSearchBar
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.design.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.esekiels.cinelex.core.design.theme.CinelexTheme

@Composable
fun CinelexSearchBar(
	query: String,
	onQueryChanged: (String) -> Unit,
	placeholder: String = "",
	modifier: Modifier = Modifier,
) {
	TextField(
		value = query,
		onValueChange = onQueryChanged,
		placeholder = {
			Text(
				text = placeholder,
				color = CinelexTheme.colors.textSecondary,
			)
		},
		leadingIcon = {
			Icon(
				imageVector = Icons.Default.Search,
				contentDescription = null,
				tint = CinelexTheme.colors.textSecondary,
			)
		},
		trailingIcon = {
			if (query.isNotEmpty()) {
				IconButton(onClick = { onQueryChanged("") }) {
					Icon(
						imageVector = Icons.Default.Close,
						contentDescription = null,
						tint = CinelexTheme.colors.textSecondary,
					)
				}
			}
		},
		singleLine = true,
		shape = RoundedCornerShape(12.dp),
		colors = TextFieldDefaults.colors(
			focusedContainerColor = CinelexTheme.colors.textSecondary.copy(alpha = 0.12f),
			unfocusedContainerColor = CinelexTheme.colors.textSecondary.copy(alpha = 0.12f),
			focusedTextColor = CinelexTheme.colors.textPrimary,
			unfocusedTextColor = CinelexTheme.colors.textPrimary,
			cursorColor = CinelexTheme.colors.primary,
			focusedIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent,
		),
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
	)
}

@Preview
@Composable
private fun CinelexSearchBarPreview() {
	CinelexTheme {
		CinelexSearchBar(
			query = "",
			onQueryChanged = {},
			placeholder = "Search movies…",
		)
	}
}
