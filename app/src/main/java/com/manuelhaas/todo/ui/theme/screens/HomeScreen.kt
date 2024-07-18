package com.manuelhaas.todo.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.manuelhaas.todo.ui.theme.components.TodoCard
import com.manuelhaas.todo.ui.theme.navigation.AppScreens
import com.manuelhaas.todo.ui.theme.viewmodel.Todo
import com.manuelhaas.todo.ui.theme.viewmodel.TodoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, todoViewModel: TodoViewModel = viewModel()) {
    val todos by todoViewModel.todos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var todoToDelete by remember { mutableStateOf<Todo?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(bottom = 10.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Column {
                        Text(
                            "My Day",
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                "30 de Agosto",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = AppScreens.TodoScreen.route) },
                modifier = Modifier.padding(end = 35.dp, bottom = 10.dp),
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(todos) { todoItem ->
                TodoCard(
                    id = todoItem.id,
                    todoName = todoItem.todoName,
                    tag = todoItem.tag,
                    date = todoItem.date,
                    selectedDate = todoItem.selectedDate,
                    checked = todoItem.isChecked,
                    isFavorite = todoItem.isFavorite,
                    onFavoriteClick = { todoId ->
                        todoViewModel.toggleFavorite(todoId)
                    },
                    onCheckedChange = { todoId, isChecked ->
                        todoViewModel.updateCheckedState(todoId, isChecked)
                    },
                    onClick = {
                        navController.navigate(AppScreens.EditTodoScreen.createRoute(todoItem.id))
                    },
                    onLongPress = { todo ->
                        todoToDelete = todo
                        showDialog = true
                    }
                )
            }
        }
        if (showDialog && todoToDelete != null) {
            ShowDialogToDeleteTodo(
                todo = todoToDelete!!,
                todoViewModel = todoViewModel,
                onDismiss = {
                    showDialog = false // Cerrar el diálogo
                    todoToDelete = null // Restablecer el TODO a eliminar
                }
            )
        }
    }
}



