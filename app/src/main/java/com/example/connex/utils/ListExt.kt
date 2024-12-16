package com.example.connex.utils

import android.content.ContentResolver
import android.os.Build
import com.example.domain.model.login.CallLog
import android.provider.ContactsContract
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.data.datastore.TokenManager
import com.example.domain.entity.contact.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.coroutines.coroutineContext

fun syncContact(resolver: ContentResolver): MutableList<Contact> {
    val contactList = mutableListOf<Contact>()

    resolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null,
        null,
        null
    )?.use { cursor ->
        while (cursor.moveToNext()) {
            val phone = cursor.getString(1)
            val formatPhone = phone.formatPhoneDashNumber()
            contactList.add(Contact(cursor.getString(0), formatPhone))
        }
    }
    return contactList
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun syncCallLog(
    tokenManager: TokenManager,
    resolver: ContentResolver,
    size: Int,
): MutableList<CallLog> {
    val callLogs = mutableListOf<CallLog>()
    val callLogLastDate = tokenManager.getCallLogLastDayToken().first()

    val selection = if (callLogLastDate != null) {
        "${android.provider.CallLog.Calls.CACHED_NAME} IS NOT NULL AND ${android.provider.CallLog.Calls.CACHED_FORMATTED_NUMBER} IS NOT NULL AND ${android.provider.CallLog.Calls.DATE} > $callLogLastDate"
    } else {
        "${android.provider.CallLog.Calls.CACHED_NAME} IS NOT NULL AND ${android.provider.CallLog.Calls.CACHED_FORMATTED_NUMBER} IS NOT NULL"
    }

    Log.d("daeyoung", "callLogLastDate: $callLogLastDate")
    Log.d("daeyoung", "selection: $selection")

    resolver.query(
        android.provider.CallLog.Calls.CONTENT_URI,
        arrayOf(
            android.provider.CallLog.Calls.CACHED_FORMATTED_NUMBER,
            android.provider.CallLog.Calls.CACHED_NAME,
            android.provider.CallLog.Calls.DURATION,
            android.provider.CallLog.Calls.DATE,
            android.provider.CallLog.Calls.TYPE,
        ),
        selection,
        null,
        null
    )?.use { cursor ->

        var count = 0
        while (cursor.moveToNext()) {
            if (count++ == size) break
            val formatPhone = cursor.getString(0)
            val name = cursor.getString(1)
            val duration = cursor.getString(2)
            val startDate = cursor.getString(3)
            val type = cursor.getString(4)

            if (count == 1) {
                CoroutineScope(coroutineContext).launch {
                    Log.d("daeyoung", "startDate: $startDate")
                    tokenManager.saveCallLogLastDayToken(startDate)
                }
            }


            val currentDateTime =
                Instant.ofEpochMilli(startDate.toLong()).atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            val formatStartDate = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss")
                .format(currentDateTime)

            // name이 null일 수 있음, 전화번호에 저장되지 않고 모르는 번호로 전화온 case
            callLogs.add(
                CallLog(
                    name,
                    formatPhone,
//                    duration.toLong(),
                    duration,
                    formatStartDate,
                    type.toInt()
                )
            )
        }

        Log.d("daeyoung", "callLogs: $callLogs")
    }
    return callLogs
}