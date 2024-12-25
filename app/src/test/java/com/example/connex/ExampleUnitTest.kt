package com.example.connex

import com.example.connex.utils.toFormatTime
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun formatTime() {
        val time = 72
        assertEquals("1분 12초", time.toFormatTime())
    }
}