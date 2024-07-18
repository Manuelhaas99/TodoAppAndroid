package com.manuelhaas.todo.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import com.manuelhaas.todo.ui.theme.components.formatDateWithYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime


data class Todo(
    val id: Int = 0,
    val todoName: String = "",
    val tag: String = "",
    val date: String = LocalDateTime.now().formatDateWithYear(),
    var isFavorite: Boolean = false,
    var isChecked: Boolean = false,
)

class TodoViewModel : ViewModel() {
    private val _state = MutableStateFlow(Todo())
    val state = _state.asStateFlow()

    private val _todos = MutableStateFlow<MutableList<Todo>>(mutableListOf())
    val todos: StateFlow<List<Todo>> = _todos

    fun updateTodoName(newTodoName: String) {
        _state.update {
            it.copy(todoName = newTodoName)
        }
    }

    fun updateTag(newTag: String) {
        _state.update {
            it.copy(tag = newTag)
        }
    }

    fun updateDate(newDate: String) {
        _state.update {
            it.copy(  date = newDate )
        }
    }

    fun addTodo() {
        if (_state.value.todoName.isNotEmpty() && _state.value.tag.isNotEmpty() && _state.value.date.isNotEmpty()) {
            val newTodo = Todo(
                id = _todos.value.size + 1,
                todoName = _state.value.todoName,
                tag = _state.value.tag,
                date = _state.value.date,
            )
            _todos.update { todos ->
                todos.toMutableList().apply {
                    add(newTodo)
                }
            }
            clearNewTodoFields()
        }

    }

    fun updateTodo(todoId: Int) {
        _todos.update { todos ->
//            todos.filter { it.id == todoId }.getOrNull(0)?.let { todo ->
//                todo.copy(
//                    todoName = _state.value.todoName,
//                    tag = _state.value.tag,
//                    date = _state.value.date
//                )
//            }
            todos.map { if (it.id == todoId) {
                    it.copy(
                        todoName = _state.value.todoName,
                        tag = _state.value.tag,
                        date = _state.value.date
                    )
                } else {
                    it
                }
            }.toMutableList()
        }
        clearNewTodoFields()
    }

    fun getTodoById(todoId: Int): Todo? {
        return _todos.value.find { it.id == todoId }
    }

    fun deleteTodoById(todoId: Int) {
        _todos.update { todos ->
            todos.filterNot { it.id == todoId }.toMutableList()
        }
    }

    fun loadTodoForEdit(todoId: Int) {
        val todo = getTodoById(todoId)
        todo?.let{
            _state.update {
                it.copy(
                    todoName = todo.todoName,
                    tag = todo.tag,
                    date = todo.date
                )
            }
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
            }.sortedByDescending { it.isFavorite }.toMutableList()
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
            }.toMutableList()
        }
    }

    private fun clearNewTodoFields() {
        _state.update {
            it.copy(
                todoName = "",
                tag = "",
                date = "",
            )
        }
    }
}


