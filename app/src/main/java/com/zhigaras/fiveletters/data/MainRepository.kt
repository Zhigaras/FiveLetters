package com.zhigaras.fiveletters.data

interface MainRepository {
    
    fun randomWord(): String
    
    class Base(
        private val wordDao: WordDao
    ) : MainRepository {
        
        private val dictionary = listOf("слово", "бегун", "тариф", "выдох")
        
        override fun randomWord(): String {
            return dictionary.random()
        }
    }
}