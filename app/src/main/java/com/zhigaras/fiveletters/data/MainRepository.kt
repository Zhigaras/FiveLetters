package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.Word
import kotlin.random.Random

interface MainRepository {
    
    suspend fun randomWord(): Word
    
    suspend fun getDictionarySize()
    
    suspend fun isWordValid(word: String): Boolean
    
    class Base(
        private val wordDao: WordDao
    ) : MainRepository {
        
        private var dictionarySize = 0
        
        override suspend fun getDictionarySize() {
            dictionarySize = wordDao.getDictionarySize()
        }
        
        override suspend fun randomWord(): Word {
            return wordDao.getRandomWord(Random.nextInt(dictionarySize))
        }
        
        override suspend fun isWordValid(word: String): Boolean {
            return wordDao.isWordExist(word)
        }
    }
}