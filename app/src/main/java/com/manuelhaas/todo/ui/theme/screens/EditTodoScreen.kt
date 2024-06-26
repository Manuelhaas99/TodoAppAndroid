package com.manuelhaas.todo.ui.theme.screens


import android.annotation.SuppressLint
import android.nfc.Tag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manuelhaas.todo.ui.theme.viewmodel.Todo
import com.manuelhaas.todo.ui.theme.viewmodel.TodoViewModel
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditTodoScreen(navController: NavController, todoViewModel: TodoViewModel, todoId: Int) {

    // Cargar la tarea para edición
    LaunchedEffect(todoId) {
        todoViewModel.loadTodoForEdit(todoId)
    }

    val todo = remember { todoViewModel.getTodoById(todoId) }
    // Inicializar los estados con los valores de la tarea
    var todoName by rememberSaveable { mutableStateOf(todo?.todoName ?: "") }
    var tag by rememberSaveable { mutableStateOf(todo?.tag ?: "") }
    var date by rememberSaveable { mutableStateOf(todo?.date ?: "") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Edit todo") },
                navigationIcon = {
                    IconButton(onClick = {
                        todo?.let {
                            todoViewModel.updateTodo(it.id, todoName, tag, date)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            TextField(
                value = todoViewModel.editTodoName,
                onValueChange = {
                    todoViewModel.editTodoName = it
                    todoName = it
                },
                placeholder = { Text("Title", style = TextStyle(fontSize = 24.sp)) },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )
            TextField(
                value = todoViewModel.editTag,
                onValueChange = {
                    todoViewModel.editTag = it
                    tag = it
                },
                placeholder = { Text("Your next task", style = TextStyle(fontSize = 18.sp)) },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )
            TextField(
                value = todoViewModel.editDate,
                onValueChange = {
                    todoViewModel.editDate = it
                    date = it
                },
                placeholder = { Text("Date", style = TextStyle(fontSize = 18.sp)) },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )
        }
    }
}