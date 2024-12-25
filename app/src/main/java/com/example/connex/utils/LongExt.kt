package com.example.connex.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

// Long을 LocalDateTime으로 변환하고 String으로 반환
@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateTime(): String {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.of("Asia/Seoul")
    ).format(DateTimeFormatter.ofPattern("yyMMddHHmmss"))
}

// second -> hour, min, second
fun Int.toFormatTime(): String {
    val callTimeList = mutableListOf<Int>()
    var callTime = this
    while (callTime > 0) {
        callTimeList.add(0, callTime % 60)
        callTime /= 60
    }
    return when(callTimeList.size) {
        3 -> {
            "${callTimeList[0]}시간 ${callTimeList[1]}분 ${callTimeList[2]}초"
        }
        2 -> {
            "${callTimeList[0]}분 ${callTimeList[1]}초"
        }
        1 -> {
            "${callTimeList[0]}초"
        }
        else -> "0"
    }
}

fun Int.toWon(): String =
    DecimalFormat("#,###").format(this)


// KB를 크기에 맞게 변환
fun Long.toMemorySizeAndUnit() =
    if (this / 1_000_000 > 0) {
        // GB로 변환
        "${kbToGB(this)}" to "GB"
    } else if (this / 1_000 > 0) {
        // MB로 변환
        "${kbToMB(this)}" to "MB"
    } else {
        // KB 유지
        "$this" to "KB"
    }

fun kbToMB(memory: Long) = (memory / 100f).roundToInt() / 10f
fun kbToGB(memory: Long) = (memory / 100_000f).roundToInt() / 10f