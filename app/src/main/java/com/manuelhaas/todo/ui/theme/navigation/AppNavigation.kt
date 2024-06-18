package com.manuelhaas.todo.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manuelhaas.todo.ui.theme.screens.HomeScreen
import com.manuelhaas.todo.ui.theme.screens.TodoScreen


@Composable
fun AppNavigation () {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = AppScreens.TodoScreen.route) {
            TodoScreen(navController)
        }
    }
}

