package com.manuelhaas.todo.ui.theme.components



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    initialDate: String?,
    onDateSelected: (String) -> Unit,
    initialSelectedDate: Long = 0
) {
    val calculatedInitialDate = if (initialSelectedDate != 0L) initialSelectedDate else LocalDateTime.now().convertDateToMillis()
    var selectedDate by remember { mutableStateOf(initialDate ?: "") }
    var showDialog by remember { mutableStateOf(false) }
    // This will ensure datePickerState is initialized once the component is ready
    var datePickerState by remember { mutableStateOf<DatePickerState?>(null) }

    if (initialDate == null) {
        return
    }

    LaunchedEffect(Unit) {
        datePickerState = DatePickerState(
            locale = java.util.Locale.getDefault(),
            initialSelectedDateMillis = calculatedInitialDate,
        )
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15 .dp)
    ) {
            OutlinedTextField(
                value = selectedDate,
                onValueChange = { selectedDate = it },
                label = { Text("Select Date") },
                readOnly = true,
                leadingIcon = {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Selected Date",
                        modifier = Modifier.clickable { showDialog = true }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDialog = true
                    }
            )
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState?.selectedDateMillis?.let { millis ->
                        val formattedDate = millis.formatDateFromMillis()
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
            datePickerState?.let { DatePicker(state = it) }
        }
    }
}

