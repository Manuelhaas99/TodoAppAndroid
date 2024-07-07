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
    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String> get() = _selectedDate

    // Estados para la creación de una nueva TODO
    private val _todoName = MutableStateFlow("")
    val todoName: StateFlow<String> get() = _todoName

    private val _tag = MutableStateFlow("")
    val tag: StateFlow<String> get() = _tag

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> get() = _date

    // Estados para la edición de una Todo Existente
    private var _editTodoName = mutableStateOf("")
    var editTodoName: String
        get() = _editTodoName.value
        set(value) {
            _editTodoName.value = value
        }

    private var _editTag = mutableStateOf("")
    var editTag: String
        get() = _editTag.value
        set(value) {
            _editTag.value = value
        }

    private var _editDate = mutableStateOf("")
    var editDate: String
        get() = _editDate.value
        set(value) {
            _editDate.value = value
        }

    private val _currentTodoId = MutableStateFlow(0)
    val currentTodoId: StateFlow<Int> get() = _currentTodoId

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> get() = _todos

    fun updateTodoName(newTodoName: String) {
        _todoName.value = newTodoName
    }

    fun updateTag(newTag: String) {
        _tag.value = newTag
    }

    fun updateDate(newDate: String) {
        _date.value = newDate
    }

    fun addTodo() {
        if (_todoName.value.isNotEmpty() && _tag.value.isNotEmpty() && _date.value.isNotEmpty()) {
            val newTodo = Todo(
                todoName = _todoName.value,
                tag = _tag.value,
                date = _date.value,
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
    fun getTodoById(todoId: Int): Todo? {
        return _todos.value.find { it.id == todoId }
    }

    fun deleteTodoById(todoId: Int) {
        _todos.update { todos ->
            todos.filterNot { it.id == todoId }
        }
    }

    fun loadTodoForEdit(todoId: Int) {
        getTodoById(todoId)?.let { todo ->
            _currentTodoId.value = todoId
            _editTodoName.value = todo.todoName
            _editTag.value = todo.tag
            _editDate.value = todo.date
        }
    }

    fun toggleFavorite(todoId: Int) {
        _todos.update { currentTodos ->
            currentTodos.map { todo ->
                if (todo.id == todoId) {
                    todo.copy(isFavorite = !todo.isFavorite)
                } else {
                    todo
                }
            }.sortedByDescending { it.isFavorite }
        }
    }

    fun updateCheckedState(todoId: Int, isChecked: Boolean) {
        _todos.update { currentTodos ->
            currentTodos.map { todo ->
                if (todo.id == todoId) {
                    todo.copy(isChecked = isChecked)
                } else {
                    todo
                }
            }
        }
    }

    private fun clearNewTodoFields() {
        _todoName.value = ""
        _tag.value = ""
        _date.value = ""
    }
}


