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
