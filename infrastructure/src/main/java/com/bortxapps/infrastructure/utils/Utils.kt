package com.bortxapps.infrastructure.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object Utils {
    const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
}

fun String.toDate(): Date? {
    val formatter = SimpleDateFormat(Utils.DATE_FORMAT, Locale.getDefault())
    return try {
        formatter.parse(this)
    } catch (ex: Exception) {
        null
    }
}

fun Date.toDataBaseString(): String {
    val formatter = SimpleDateFormat(Utils.DATE_FORMAT, Locale.getDefault())
    return formatter.format(this)
}