package com.example.connex.utils

import java.io.File

fun File.allDelete():Boolean? {
    var returnData = false
    val result = runCatching {     if (this.exists()) {
        val files = this.listFiles()
        if (files != null && files.size >0) {
            files.forEachIndexed { index, file ->
                returnData = file.delete()
            }
            return returnData
        } else {
            return false
        }
    } else {
        return false
    } }.onSuccess { return true }.onFailure { error -> return false }

    return result.getOrNull()
}