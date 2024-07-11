package com.manuelhaas.todo.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.manuelhaas.todo.ui.theme.White
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    initialDate: String? = null,
    onDateSelected: (String) -> Unit
) {

    var selectedDate by remember { mutableStateOf(initialDate ?: "") }
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    var showDialog by remember { mutableStateOf(false) }
    // This will ensure datePickerState is initialized once the component is ready
    var datePickerState by remember { mutableStateOf<DatePickerState?>(null)
    }

    LaunchedEffect(Unit) {
        val initialMillis = initialDate?.let {
            LocalDate.parse(it, dateFormatter).toEpochDay() * 24 * 60 * 60 * 1000
        } ?: (LocalDate.now().toEpochDay() * 24 * 60 * 60 * 1000)

        datePickerState = DatePickerState(
            initialSelectedDateMillis = initialMillis,
            locale = java.util.Locale.getDefault()
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Selected Date:  $selectedDate",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            color = White
        )
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Pick a Date")
        }
    }

    if (showDialog && datePickerState != null) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState?.selectedDateMillis?.let { millis ->
                        val formattedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000)).format(dateFormatter)
                        selectedDate = formattedDate
                        onDateSelected(formattedDate)
                    }
                    showDialog = false
                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            DatePicker(state = datePickerState!!)
        }
    }
}
