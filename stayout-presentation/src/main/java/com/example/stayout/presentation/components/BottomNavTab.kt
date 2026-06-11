package com.example.stayout.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.stayout.presentation.R

sealed class BottomNavTab(
    val route: String,
    @StringRes val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    data object Explore : BottomNavTab(
        route = "list",
        labelRes = R.string.nav_explore,
        selectedIcon = Icons.Filled.Explore,
        unselectedIcon = Icons.Outlined.Explore,
    )

    data object Saved : BottomNavTab(
        route = "saved",
        labelRes = R.string.nav_saved,
        selectedIcon = Icons.Filled.Bookmark,
        unselectedIcon = Icons.Outlined.BookmarkBorder,
    )

    data object Bookings : BottomNavTab(
        route = "bookings",
        labelRes = R.string.nav_bookings,
        selectedIcon = Icons.Filled.CalendarMonth,
        unselectedIcon = Icons.Outlined.CalendarMonth,
    )

    data object Profile : BottomNavTab(
        route = "profile",
        labelRes = R.string.nav_profile,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    )

    companion object {
        val all = listOf(Explore, Saved, Bookings, Profile)
    }
}
