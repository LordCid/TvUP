package com.example.omapp.common

import com.example.omapp.EMPTY_STRING
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val FORMAT_DATE_LONG_TIME = "HH:mm"

fun Long.formatDuration() : String {
    return try {
        SimpleDateFormat(FORMAT_DATE_LONG_TIME, Locale.getDefault()).format(Date(this))
    } catch (e: ParseException) { EMPTY_STRING }
}
