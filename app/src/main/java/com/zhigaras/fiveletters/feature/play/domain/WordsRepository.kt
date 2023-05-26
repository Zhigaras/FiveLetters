package com.zhigaras.fiveletters.feature.play.domain

interface WordsRepository {
    
    suspend fun getNewWord(): String
    
    suspend fun isWordValid(word: String): Boolean
}