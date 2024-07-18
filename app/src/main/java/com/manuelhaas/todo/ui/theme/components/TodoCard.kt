package com.manuelhaas.todo.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavController
import com.manuelhaas.todo.R
import com.manuelhaas.todo.ui.theme.navigation.AppScreens
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
    selectedDate: String,
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
                         onLongPress(Todo(id, todoName, tag, date, selectedDate, checked, isFavorite))
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