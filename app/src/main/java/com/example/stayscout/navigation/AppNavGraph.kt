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
import com.example.stayout.presentation.list.PropertyListEvent
import com.example.stayout.presentation.list.PropertyListScreen

private const val ROUTE_LIST = "list"
private const val ROUTE_DETAIL = "detail/{propertyId}"
private const val ARG_PROPERTY_ID = "propertyId"

@Composable
fun AppNavGraph(
    onToggleTheme: () -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_LIST,
    ) {
        composable(ROUTE_LIST) {
            PropertyListScreen(
                onEvent = { event ->
                    when (event) {
                        is PropertyListEvent.NavigateToDetail ->
                            navController.navigate("detail/${event.propertyId}")
                        PropertyListEvent.ToggleTheme -> onToggleTheme()
                    }
                },
            )
        }
        composable(
            route = ROUTE_DETAIL,
            arguments = listOf(navArgument(ARG_PROPERTY_ID) { type = NavType.IntType }),
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
