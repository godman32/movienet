package com.gm.mvies.feature.helper

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by @godman on 16/06/23.
 */

fun String.toDate(format:String):Date{
    val inputFormatter: DateFormat = SimpleDateFormat(format)
    inputFormatter.setTimeZone(TimeZone.getTimeZone("UTC"))

    return inputFormatter.parse(this) as Date
}