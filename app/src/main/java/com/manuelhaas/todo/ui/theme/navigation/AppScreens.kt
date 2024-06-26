package com.manuelhaas.todo.ui.theme.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen : AppScreens("home_screen")
    object TodoScreen : AppScreens("todo_screen")
    object EditTodoScreen : AppScreens("edit_todo_screen/{todoId}") {
        fun createRoute(todoId: Int) = "edit_todo_screen/$todoId"
    }
}
