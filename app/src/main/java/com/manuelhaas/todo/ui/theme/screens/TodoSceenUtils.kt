package com.manuelhaas.todo.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.manuelhaas.todo.ui.theme.viewmodel.Todo
import com.manuelhaas.todo.ui.theme.viewmodel.TodoViewModel


@Composable
fun ShowDialogToDeleteTodo(
    todo: Todo,
    todoViewModel: TodoViewModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Eliminar TODO") },
        text = { Text(text = "¿Estás seguro de que deseas eliminar este TODO?") },
        confirmButton = {
            Button(
                onClick = {
                    todoViewModel.deleteTodoById(todo.id)
                    onDismiss()
                }
            ) {
                Text(text = "Eliminar")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text(text = "Cancelar")
            }
        }
    )
}

