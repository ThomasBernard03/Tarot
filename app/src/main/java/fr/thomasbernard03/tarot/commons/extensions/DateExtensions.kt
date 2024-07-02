package fr.thomasbernard03.tarot.commons.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toPrettyDate() : String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(this)
}

fun Date.toPrettyTime() : String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(this)
}