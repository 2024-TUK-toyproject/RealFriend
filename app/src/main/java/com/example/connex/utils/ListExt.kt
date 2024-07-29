package com.example.connex.utils

import android.content.ContentResolver
import android.provider.ContactsContract
import com.example.domain.model.Contact

fun syncContact(resolver: ContentResolver):MutableList<Contact> {
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
            val formatPhone = StringBuilder(phone)
            if (phone.slice(0..2) == "010") {
                if (phone[3] != '-') {
                    formatPhone.insert(3, "-")
                }
                if (formatPhone[3] == '-' && phone[8] != '-') {
                    formatPhone.insert(8, "-")
                }
            }
            contactList.add(Contact(cursor.getString(0), formatPhone.toString()))
        }
    }
    return contactList
}