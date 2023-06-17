package com.gm.mvies.feature.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by @godman on 16/06/23.
 */

@SuppressLint("SimpleDateFormat")
fun Date.format(format: String):String{
    val df = SimpleDateFormat(format)

    return df.format(this)
}