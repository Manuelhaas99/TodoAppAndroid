package com.manuelhaas.todo.ui.theme.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class Todo(
    val todoName: String,
    val tag: String,
    val date: String,
    var isFavorite: Boolean = false,
    var isChecked: Boolean = false,
)

class TodoViewModel : ViewModel() {
    var todoName by mutableStateOf("")
    var tag by mutableStateOf("")
    var todoDate by mutableStateOf("")

    private val _todos = mutableStateListOf<Todo>()
    val todos: List<Todo> get() = _todos

    fun addTodo() {
        if (todoName.isNotEmpty() && todoName.isNotEmpty()) {
            val newTodo = Todo(
                todoName = todoName,
                tag = tag,
                date = todoDate,
            )
            _todos.add(newTodo)
            todoName = ""
            tag = ""
            todoDate = ""
        }
    }
}

