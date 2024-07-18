package com.manuelhaas.todo.ui.theme.components


import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


fun LocalDateTime.formatDateWithYear(): String {
    val format = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
    return this.format(format)
}

fun Long.formatDateFromMillis(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    return java.time.LocalDate.ofEpochDay(this / (24 * 60 * 60 * 1000)).format(dateFormatter)
}

fun LocalDateTime.convertDateToMillis(): Long {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

