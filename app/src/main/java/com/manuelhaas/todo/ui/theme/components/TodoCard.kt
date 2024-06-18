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
import com.manuelhaas.todo.R

@Composable
fun TodoCard(
    todoName: String,
    tag: String,
    todoDate: String,
    isFavorite: Boolean,
    checked: Boolean,
    onFavoriteClick: (state: Boolean) -> Unit,
    onCheckedChange: (state: Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
        ) {
            Column {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { onCheckedChange(it) },
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = todoName)
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = tag, modifier = Modifier.padding(end = 12.dp))
                    Text(text = todoDate)
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.height(45.dp),
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "",
                    Modifier.clickable { onFavoriteClick(!isFavorite) }
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