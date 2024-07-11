package com.manuelhaas.todo.ui.theme.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.manuelhaas.todo.ui.theme.components.DatePickerComponent
import com.manuelhaas.todo.ui.theme.viewmodel.TodoViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
//TODO/ this component should have its own viewmodel (?)
fun AddTodoScreen(navController: NavController, todoViewModel: TodoViewModel = viewModel()) {
    //TODO/ The viewmodel should handle the state of the input texts
    val state by todoViewModel.state.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Create Todo") },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (state.todoName.isBlank() || state.tag.isBlank() || state.date.isBlank()) {
                                snackbarHostState.showSnackbar("All fields must be filled")
                            } else {
                                todoViewModel.addTodo()
                                navController.popBackStack()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
            )
        },
        snackbarHost = {
            // Snackbar host to display snackbar messages
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            TextField(
                value = state.todoName,
                onValueChange = { todoViewModel.updateTodoName(it) },
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
                initialDate = todoViewModel.selectedDate.value,
                onDateSelected = { selectedDate ->
                    todoViewModel.updateDate(selectedDate)
                }
            )
        }
    }
}