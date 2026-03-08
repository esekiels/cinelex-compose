/*
 * Cinelex
 * CinelexBottomBar
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.design.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import co.esekiels.cinelex.core.design.theme.CinelexTheme

@Composable
fun CinelexBottomBar(
	items: List<BottomBarItem>,
	selectedIndex: Int,
	onItemSelected: (Int) -> Unit,
) {
	NavigationBar(
		containerColor = CinelexTheme.colors.background,
		windowInsets = WindowInsets(0),
	) {
		items.forEachIndexed { index, item ->
			val selected = index == selectedIndex
			NavigationBarItem(
				selected = selected,
				onClick = { if (!selected) onItemSelected(index) },
				icon = { Icon(item.icon, contentDescription = null) },
				label = { Text(item.label) },
				colors = NavigationBarItemDefaults.colors(
					selectedIconColor = CinelexTheme.colors.primary,
					selectedTextColor = CinelexTheme.colors.primary,
					unselectedIconColor = CinelexTheme.colors.textSecondary,
					unselectedTextColor = CinelexTheme.colors.textSecondary,
					indicatorColor = CinelexTheme.colors.background,
				),
			)
		}
	}
}

data class BottomBarItem(
	val icon: ImageVector,
	val label: String,
)

@Preview
@Composable
private fun CinelexBottomBarPreview() {
	CinelexTheme {
		CinelexBottomBar(
			items = listOf(
				BottomBarItem(Icons.Filled.Home, "Home"),
				BottomBarItem(Icons.Filled.Search, "Search"),
			),
			selectedIndex = 0,
			onItemSelected = {},
		)
	}
}
