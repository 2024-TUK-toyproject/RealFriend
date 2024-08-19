package com.example.connex.utils

fun String.formatPhoneDashNumber(): String  {
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