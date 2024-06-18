package com.manuelhaas.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.manuelhaas.todo.ui.theme.TODOTheme
import com.manuelhaas.todo.ui.theme.navigation.AppNavigation


import com.manuelhaas.todo.ui.theme.screens.HomeScreen



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODOTheme {
                AppNavigation()
            }
        }
    }
}

