package com.example.domain.model

data class Phone(
    val number: String,
    val mobileCarrier: MobileCarrier
) {
    fun checkValidation(): Boolean = number.length == PHONE_NUMBER_LENGTH

    companion object {
        const val PHONE_NUMBER_LENGTH = 11

        fun default(): Phone {
            return Phone("010", MobileCarrier.SKT)
        }
    }
}
