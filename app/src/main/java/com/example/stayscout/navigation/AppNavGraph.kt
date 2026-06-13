package com.example.stayscout.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stayout.presentation.detail.PropertyDetailEvent
import com.example.stayout.presentation.detail.PropertyDetailScreen
import com.example.stayout.presentation.home.HomeScreen

@Composable
fun AppNavGraph(
    onThemeChange: (Boolean) -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List,
    ) {
        composable<Screen.List> {
            HomeScreen(
                onNavigateToDetail = { propertyId ->
                    navController.navigate(Screen.Detail(propertyId = propertyId))
                },
                onThemeChange = onThemeChange,
            )
        }
        composable<Screen.Detail> {
            PropertyDetailScreen(
                onEvent = { event ->
                    when (event) {
                        PropertyDetailEvent.NavigateBack -> navController.popBackStack()
                        else -> Unit
                    }
                },
            )
        }
    }
}
