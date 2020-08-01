package com.ramadan.data

import org.jsoup.Jsoup
import java.net.UnknownHostException

class TruecallerDataRepositoryImpl() : TruecallerDataRepository {

    private val URL = "https://truecaller.blog/2018/01/22/life-as-an-android-engineer/"

    var inMemoryData: String = ""

    override fun getTruecallerData(): String {
        val stringBuilder = StringBuilder()
        try {
            val doc = Jsoup.connect(URL).get()
            val paragraphs = doc.select("p")
            paragraphs.forEach {
                stringBuilder.append(it.text())
            }
            inMemoryData = stringBuilder.toString()
        } catch (e: Exception) {
            if (e is UnknownHostException)
                throw UnknownHostException()
            else
                throw IllegalArgumentException("content is empty")
        }
        return inMemoryData
    }

    override fun get10thCharacter(): String {
        if (inMemoryData.isEmpty())
            getTruecallerData()
        return inMemoryData.substring(10, 11)
    }

    override fun getEvery10thCharacter(): String {
        if (inMemoryData.isEmpty())
            getTruecallerData()
        return getEvery10thArrayList(inMemoryData).toString()
    }

    override fun getWordsCount(): String {
        if (inMemoryData.isEmpty())
            getTruecallerData()
        return countWords(inMemoryData)
    }

    private fun getEvery10thArrayList(str: String): ArrayList<Char> {
        val list = ArrayList<Char>()
        for (i in 10 until (str.length) step 10)
            list.add(str[i])
        return list
    }

    private fun countWords(str: String): String {
        val map = hashMapOf<String, Int?>()
        val wordList: List<String> = str.trim().split(" ")
        for (word in wordList)
            if (map.containsKey(word))
                map[word] = map[word]?.plus(1)
            else
                map[word] = 1

        return map.toString()
    }

}