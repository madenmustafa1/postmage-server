package com.postmage.util

import java.util.*

object DateUtil {
    fun getTimeNow() = Calendar.getInstance(Locale.getDefault()).timeInMillis
}