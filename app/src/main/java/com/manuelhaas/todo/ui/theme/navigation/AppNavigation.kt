package com.manuelhaas.todo.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manuelhaas.todo.ui.theme.screens.EditTodoScreen
import com.manuelhaas.todo.ui.theme.screens.HomeScreen
import com.manuelhaas.todo.ui.theme.screens.TodoScreen
import com.manuelhaas.todo.ui.theme.viewmodel.TodoViewModel


@Composable
fun AppNavigation () {
    val navController = rememberNavController()
    val todoViewModel: TodoViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController, todoViewModel)
        }
        composable(route = AppScreens.TodoScreen.route) {
            TodoScreen(navController, todoViewModel)
        }
        composable(route = AppScreens.EditTodoScreen.route) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")?.toIntOrNull()
            if (todoId != null) {
                EditTodoScreen(navController, todoViewModel, todoId)
            }
        }
    }
}

