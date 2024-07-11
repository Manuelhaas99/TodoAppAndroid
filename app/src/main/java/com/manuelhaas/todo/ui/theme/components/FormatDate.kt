package com.manuelhaas.todo.ui.theme.components


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import java.util.Locale


fun LocalDateTime.formatDateWithYear(): String {
    val format = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
    return this.format(format)
}
