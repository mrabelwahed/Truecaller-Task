package com.ramadan.data

interface TruecallerDataRepository {
    fun getTruecallerData(): String
    fun get10thCharacter(): String
    fun getEvery10thCharacter(): String
    fun getWordsCount(): String
}