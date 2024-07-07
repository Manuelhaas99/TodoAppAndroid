package com.manuelhaas.todo.ui.theme.screens


import android.annotation.SuppressLint
import android.nfc.Tag
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manuelhaas.todo.ui.theme.components.DatePickerComponent
import com.manuelhaas.todo.ui.theme.viewmodel.Todo
import com.manuelhaas.todo.ui.theme.viewmodel.TodoViewModel
import java.util.Date
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.getValue



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun EditTodoScreen(navController: NavController, todoViewModel: TodoViewModel, todoId: Int) {

    // Cargar la tarea para edición
    LaunchedEffect(todoId) {
        todoViewModel.loadTodoForEdit(todoId)
    }

    val editTodoName = todoViewModel.editTodoName
    val editTag = todoViewModel.editTag
    var editDate by remember { mutableStateOf(todoViewModel.editDate) }
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    val onDateSelected: (String) -> Unit = { selectedDate ->
        todoViewModel.updateDate(selectedDate)
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

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
                            todoViewModel.updateTodo(it.id, editTodoName, editTag, editDate)
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
                value = editTodoName,
                onValueChange = {  todoViewModel.editTodoName = it
                },
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
                value = editTag,
                onValueChange = { todoViewModel.editTag = it
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
            Spacer(modifier = Modifier.height(8.dp))
            DatePickerComponent(
                initialDate = editDate,
                onDateSelected = { selectedDate ->
                    editDate = selectedDate // Actualiza la fecha en el estado local
                    todoViewModel.updateDate(selectedDate) // Actualiza la fecha en el ViewModel
                }
            )
        }
    }
}