package com.zhigaras.fiveletters.data

interface Repository {
    
    fun randomWord(): String
    
    class Base(
        private val wordDao: WordDao
    ) : Repository {
        
        private val dictionary = listOf("слово", "бегун", "тариф", "выдох")
        
        override fun randomWord(): String {
            return dictionary.random()
        }
    }
}