package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.Word

interface MainRepository : DatastoreManager.UserStat.Write {
    
    suspend fun randomWord(): Word
    
    suspend fun isWordValid(word: String): Boolean
    
    suspend fun updateWord(word: Word)
    
    class Base(
        private val wordDao: WordDao,
        private val userStat: DatastoreManager.UserStat.Write
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
        
        override suspend fun incrementGamesCounter() {
            userStat.incrementGamesCounter()
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
        
        override suspend fun incrementGamesCounter() {}
        
    }
}