package com.example.stayout.presentation.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stayout.presentation.R
import com.example.stayout.presentation.components.BottomNavBar
import com.example.stayout.presentation.components.BottomNavTab
import com.example.stayout.presentation.components.GeneralAppBar
import com.example.stayout.presentation.components.OfflineBanner
import com.example.stayout.presentation.home.sections.bookings.BookingsSection
import com.example.stayout.presentation.home.sections.explore.ExploreSection
import com.example.stayout.presentation.home.sections.profile.ProfileSection
import com.example.stayout.presentation.home.sections.saved.SavedSection

@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit,
    onThemeChange: (Boolean?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    tabNavController: NavHostController = rememberNavController(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentOnThemeChange by rememberUpdatedState(onThemeChange)
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }
    val backOnlineMessage = stringResource(R.string.banner_back_online_message)
    val comingSoonMessage = stringResource(R.string.coming_soon)

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeEvent.ThemeChanged -> currentOnThemeChange(event.isDarkTheme)
                HomeEvent.BackOnline -> snackbarHostState.showSnackbar(backOnlineMessage)
                HomeEvent.ShowComingSoon -> snackbarHostState.showSnackbar(comingSoonMessage)
            }
        }
    }

    val readyState = state as? HomeState.Ready
    val effectiveDark = readyState?.isDarkTheme ?: isSystemInDarkTheme()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            GeneralAppBar(
                searchQuery = readyState?.searchQuery ?: "",
                onSearchQueryChange = { viewModel.onIntent(HomeIntent.UpdateSearch(it)) },
                isDarkTheme = effectiveDark,
                onToggleTheme = { viewModel.onIntent(HomeIntent.ToggleTheme(effectiveDark)) },
                isSearchEnabled = readyState != null,
                onAvatarClick = { viewModel.onIntent(HomeIntent.TapAvatar) },
                onNotificationsClick = { viewModel.onIntent(HomeIntent.TapNotifications) },
            )
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onTabSelect = { route ->
                    tabNavController.navigate(route) {
                        popUpTo(tabNavController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            OfflineBanner(
                visible = readyState?.isOffline == true && currentRoute != BottomNavTab.Explore.route,
            )
            NavHost(
                navController = tabNavController,
                startDestination = BottomNavTab.Explore.route,
                modifier = Modifier.fillMaxSize(),
            ) {
                composable(BottomNavTab.Explore.route) {
                    ExploreSection(
                        searchQuery = readyState?.searchQuery ?: "",
                        onNavigateToDetail = onNavigateToDetail,
                    )
                }
                composable(BottomNavTab.Saved.route) { SavedSection() }
                composable(BottomNavTab.Bookings.route) { BookingsSection() }
                composable(BottomNavTab.Profile.route) { ProfileSection() }
            }
        }
    }
}
