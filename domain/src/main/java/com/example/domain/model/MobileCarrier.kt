package com.example.domain.model

enum class MobileCarrier(private val krName: String) {
    NOT("통신사 선택"),
    SKT("SKT"), KT("KT"), LG("LG+"),
    FRUGAL_SKT("SKT 알뜰폰"), FRUGAL_KT("KT 알뜰폰"), FRUGAL_LG("LG+ 알뜰폰");

    fun getName(): String {
        return this.krName
    }
}