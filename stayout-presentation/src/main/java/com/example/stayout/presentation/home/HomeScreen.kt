package com.example.stayout.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stayout.presentation.components.BottomNavBar
import com.example.stayout.presentation.components.BottomNavTab
import com.example.stayout.presentation.components.GeneralAppBar
import com.example.stayout.presentation.components.OfflineBanner
import com.example.stayout.presentation.home.sections.bookings.BookingsSection
import com.example.stayout.presentation.home.sections.explore.ExploreEvent
import com.example.stayout.presentation.home.sections.explore.ExploreIntent
import com.example.stayout.presentation.home.sections.explore.ExploreSection
import com.example.stayout.presentation.home.sections.explore.ExploreState
import com.example.stayout.presentation.home.sections.explore.ExploreViewModel
import com.example.stayout.presentation.home.sections.profile.ProfileSection
import com.example.stayout.presentation.home.sections.saved.SavedSection

@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = hiltViewModel(),
    tabNavController: NavHostController = rememberNavController(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentOnNavigateToDetail by rememberUpdatedState(onNavigateToDetail)
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is ExploreEvent.NavigateToDetail -> currentOnNavigateToDetail(event.propertyId)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            GeneralAppBar(
                searchQuery = (state as? ExploreState.Success)?.searchQuery ?: "",
                onSearchQueryChange = { viewModel.onIntent(ExploreIntent.UpdateSearch(it)) },
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
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            OfflineBanner(visible = (state as? ExploreState.Success)?.isOffline == true)
            NavHost(
                navController = tabNavController,
                startDestination = BottomNavTab.Explore.route,
                modifier = Modifier.fillMaxSize(),
            ) {
                composable(BottomNavTab.Explore.route) {
                    ExploreSection(
                        state = state,
                        onIntent = viewModel::onIntent,
                        scrollIndex = viewModel.scrollIndex,
                        onSaveScroll = viewModel::saveScrollPosition,
                    )
                }
                composable(BottomNavTab.Saved.route) { SavedSection() }
                composable(BottomNavTab.Bookings.route) { BookingsSection() }
                composable(BottomNavTab.Profile.route) { ProfileSection() }
            }
        }
    }
}
