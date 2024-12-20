package com.example.connex.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

fun String.formatPhoneDashNumber(): String {
    val formatPhone = StringBuilder(this)
    if (this.slice(0..2) == "010") {
        if (this[3] != '-') {
            formatPhone.insert(3, "-")
        }
        if (formatPhone[3] == '-' && this[8] != '-') {
            formatPhone.insert(8, "-")
        }
    }
    return formatPhone.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun toFormatDate(date: String, time: String): String {
    val (year, month, day) = date.split("-").map { it.toInt() }
    val (hour, min, _) = time.split(":").map { it.toInt() }
    val localDateTime = LocalDateTime.now()
    return when {
        year < localDateTime.year -> {
            "${localDateTime.year - year}년 전"
        }
        month < localDateTime.monthValue -> {
            "${localDateTime.monthValue - month}달 전"
        }
        day < localDateTime.dayOfMonth -> {
            "${localDateTime.dayOfMonth - day}일 전"
        }
        else -> {
            val noon = if (hour in 12..23) "오후" else "오전"
            "$noon ${hour}시 ${min}분"
        }
    }
}