package com.example.connex.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Long을 LocalDateTime으로 변환하고 String으로 반환
@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateTime(): String {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.of("Asia/Seoul")
    ).format(DateTimeFormatter.ofPattern("yyMMddHHmmss"))
}


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
