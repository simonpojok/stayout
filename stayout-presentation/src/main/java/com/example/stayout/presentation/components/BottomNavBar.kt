package com.example.stayout.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onTabSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HorizontalDivider()
        NavigationBar {
            BottomNavTab.all.forEach { tab ->
                val selected = currentRoute == tab.route
                val label = stringResource(tab.labelRes)
                NavigationBarItem(
                    selected = selected,
                    onClick = { onTabSelect(tab.route) },
                    icon = {
                        Icon(
                            imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                            contentDescription = label,
                        )
                    },
                    label = { Text(label) },
                )
            }
        }
    }
}

// region Previews

@PreviewThemes
@Composable
private fun BottomNavBarExploreSelectedPreview() {
    StayScoutTheme {
        BottomNavBar(currentRoute = "list", onTabSelect = {})
    }
}

@PreviewThemes
@Composable
private fun BottomNavBarSavedSelectedPreview() {
    StayScoutTheme {
        BottomNavBar(currentRoute = "saved", onTabSelect = {})
    }
}

// endregion
