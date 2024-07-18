package com.manuelhaas.todo.ui.theme.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manuelhaas.todo.ui.theme.components.DatePickerComponent
import com.manuelhaas.todo.ui.theme.components.convertDateToMillis
import com.manuelhaas.todo.ui.theme.components.formatDateFromMillis
import com.manuelhaas.todo.ui.theme.viewmodel.TodoViewModel
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun EditTodoScreen(navController: NavController, todoViewModel: TodoViewModel, todoId: Int) {
    val state by todoViewModel.state.collectAsState()
    // Cargar la tarea para edición
    val focusRequester = remember { FocusRequester() }
//    val selectedDate by todoViewModel.selectedDate
//    val coroutineScope = rememberCoroutineScope()
//    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(todoId) {
        todoViewModel.loadTodoForEdit(todoId)
    }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

//    val onDateSelected: (String) -> Unit = { selectedDate ->
//        todoViewModel.updateDate(selectedDate)
//    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Edit todo") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Obtener la tarea actual del ViewModel
                        val currentTodo = todoViewModel.getTodoById(todoId)
                        currentTodo?.let {
                            // Actualizar la tarea con los nuevos valores
                            todoViewModel.updateTodo(todoId = it.id)
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
            //TODO add focus to display keyboard on first render
            TextField(
                value = state.todoName,
                onValueChange = {  todoViewModel.updateTodoName(it) },
                placeholder = { Text("Title", style = TextStyle(fontSize = 24.sp)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )
            TextField(
                value = state.tag,
                onValueChange = { todoViewModel.updateTag(it) },
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
            DatePickerComponent(
                initialDate = state.selectedDate,
                onDateSelected = { todoViewModel.updateDate(it) }
            )
        }
    }
}