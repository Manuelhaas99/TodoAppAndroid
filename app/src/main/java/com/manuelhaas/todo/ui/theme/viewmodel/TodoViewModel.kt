package com.manuelhaas.todo.ui.theme.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    val selectedDate: String = LocalDateTime.now().formatDateWithYear(),
    val date: String = "",
    var isFavorite: Boolean = false,
    var isChecked: Boolean = false,
)

class TodoViewModel : ViewModel() {
    private val _state = MutableStateFlow(Todo())
    val state = _state.asStateFlow()

    private val _todos = MutableStateFlow<MutableList<Todo>>(mutableListOf())
    val todos: StateFlow<List<Todo>> get() = _todos

//    private val _selectedDate = mutableStateOf<String?>(LocalDateTime.now().formatDateWithYear())
//    val selectedDate: State<String?>
//        get() = _selectedDate

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
            it.copy(  selectedDate = newDate, date = newDate )
        }
    }

//    fun setSelectedDate(date: String) {
//        _selectedDate.value = date
//    }
//
//    fun getSelectedDate(): String? {
//        return _selectedDate.value
//    }

    fun addTodo() {
        if (_state.value.todoName.isNotEmpty() && _state.value.tag.isNotEmpty() && _state.value.selectedDate.isNotEmpty()) {
            val newTodo = Todo(
                id = _todos.value.size + 1,
                todoName = _state.value.todoName,
                tag = _state.value.tag,
                date = _state.value.date,
                selectedDate =  _state.value.selectedDate,
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
                        date = _state.value.date,
                        selectedDate = _state.value.selectedDate,
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
                    date = todo.date,
                    selectedDate = todo.selectedDate
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


