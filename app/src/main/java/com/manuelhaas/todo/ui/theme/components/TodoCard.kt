package com.manuelhaas.todo.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manuelhaas.todo.ui.theme.viewmodel.Todo
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun TodoCard(
    id: Int = 0,
    todoName: String,
    tag: String,
    date: String,
    isFavorite: Boolean,
    checked: Boolean,
    onFavoriteClick: (Int) -> Unit,
    onCheckedChange: (Int, Boolean) -> Unit,
    onClick: () -> Unit,
    onLongPress: (Todo) -> Unit
) {
    var longPressTriggered by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                 detectTapGestures(
                     onTap = { onClick() },
                     onLongPress = {
                         longPressTriggered = true
                         onLongPress(Todo(id, todoName, tag, date, checked, isFavorite))
                     }
            )
        }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
        ) {
            Column {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        onCheckedChange(id, isChecked)
                    },
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = todoName)
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = tag, modifier = Modifier.padding(end = 12.dp))
                    Text(text = date)
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.height(45.dp),
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    modifier = Modifier.clickable { onFavoriteClick(id) }
                )
            }
        }
    }
}


//@Preview
//@Composable
//fun TodoCardPreview() {
//  Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
//    TodoCard(todoName = "Prueba todo", tag = "Prueba", date = "15 de mayo", isFavorite = true)
//}