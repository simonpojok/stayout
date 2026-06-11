package com.example.stayscout.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stayout.presentation.detail.PropertyDetailEvent
import com.example.stayout.presentation.detail.PropertyDetailScreen
import com.example.stayout.presentation.home.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route,
    ) {
        composable(Screen.List.route) {
            HomeScreen(
                onNavigateToDetail = { propertyId ->
                    navController.navigate(Screen.Detail.createRoute(propertyId))
                },
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments =
                listOf(
                    navArgument(Screen.Detail.ARG_PROPERTY_ID) { type = NavType.IntType },
                ),
        ) {
            PropertyDetailScreen(
                onEvent = { event ->
                    when (event) {
                        PropertyDetailEvent.NavigateBack -> navController.popBackStack()
                    }
                },
            )
        }
    }
}
