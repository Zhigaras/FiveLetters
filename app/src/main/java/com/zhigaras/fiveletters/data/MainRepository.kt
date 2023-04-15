package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.Word

interface MainRepository {
    
    suspend fun randomWord(): Word
    
    suspend fun isWordValid(word: String): Boolean
    
    suspend fun updateWord(word: Word)
    
    class Base(
        private val wordDao: WordDao
    ) : MainRepository {
        
        override suspend fun randomWord(): Word {
            return wordDao.getUnsolvedWord()
        }
        
        override suspend fun isWordValid(word: String): Boolean {
            return wordDao.isWordExist(word)
        }
        
        override suspend fun updateWord(word: Word) {
            wordDao.update(word)
        }
    }
    
    class Fake(private val valid: Boolean) : MainRepository {
        override suspend fun randomWord(): Word {
            return Word(1, "test", false)
        }
        
        override suspend fun isWordValid(word: String): Boolean {
            return valid
        }
        
        override suspend fun updateWord(word: Word) {}
        
    }
}