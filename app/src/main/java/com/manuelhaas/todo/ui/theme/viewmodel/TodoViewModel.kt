package com.manuelhaas.todo.ui.theme.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class Todo(
    val id: Int,
    val todoName: String,
    val tag: String,
    val date: String,
    var isFavorite: Boolean = false,
    var isChecked: Boolean = false,
)

class TodoViewModel : ViewModel() {
    // Estados para la creación de una nueva TODO
    var todoName by mutableStateOf("")
    var tag by mutableStateOf("")
    var date by mutableStateOf("")

    // Estados para la edición de una Todo Existente
    var editTodoName by mutableStateOf("")
    var editTag by mutableStateOf("")
    var editDate by mutableStateOf("")
    var currentTodoId by mutableStateOf(0)

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> get() = _todos

    fun addTodo() {
        if (todoName.isNotEmpty() && tag.isNotEmpty() && date.isNotEmpty()) {
            val newTodo = Todo(
                todoName = todoName,
                tag = tag,
                date = date,
                id = _todos.value.size + 1
            )
            _todos.update { it + newTodo }
            clearNewTodoFields()
        }
    }

    fun updateTodo(todoId: Int, newTodoName: String, newTag: String, newDate: String) {
        _todos.update { todos ->
            todos.map {
                if (it.id == todoId) {
                    it.copy(
                        todoName = newTodoName,
                        tag = newTag,
                        date = newDate
                    )
                } else {
                    it
                }
            }
        }
    }
    fun deleteTodoById(todoId: Int) {
        _todos.update { todos ->
            todos.filterNot { it.id == todoId }
        }
    }
    fun getTodoById(todoId: Int): Todo? {
        return _todos.value.find { it.id == todoId }
    }

    fun loadTodoForEdit(todoId: Int) {
        getTodoById(todoId)?.let {
            currentTodoId = todoId
            editTodoName = it.todoName
            editTag = it.tag
            editDate = it.date
        }
    }
    private fun clearNewTodoFields() {
        todoName = ""
        tag = ""
        date = ""
    }
}


