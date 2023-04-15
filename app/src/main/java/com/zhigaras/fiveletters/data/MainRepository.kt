package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.Word
import kotlin.random.Random

interface MainRepository {
    
    suspend fun randomWord(): Word
    
    suspend fun saveDictionarySize()
    
    suspend fun isWordValid(word: String): Boolean
    
    class Base(
        private val wordDao: WordDao
    ) : MainRepository {
        
        private var dictionarySize = 0
        
        override suspend fun saveDictionarySize() {
            dictionarySize = wordDao.getDictionarySize()
        }
        
        override suspend fun randomWord(): Word {
            return wordDao.getWordWithOffset(Random.nextInt(dictionarySize))
        }
        
        override suspend fun isWordValid(word: String): Boolean {
            return wordDao.isWordExist(word)
        }
    }
    
    class Fake(private val valid: Boolean) : MainRepository {
        override suspend fun randomWord(): Word {
            return Word(1, "test", false)
        }
        
        override suspend fun saveDictionarySize() {}
        
        override suspend fun isWordValid(word: String): Boolean {
            return valid
        }
        
    }
}