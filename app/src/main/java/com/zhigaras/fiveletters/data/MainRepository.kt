package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.Word

interface MainRepository : MainDao, UserStatInteract.Write {
    
    class Base(
        private val wordDao: MainDao,
        private val userStatInteract: UserStatInteract.Write
    ) : MainRepository {
        
        override suspend fun getUnsolvedWord() = wordDao.getUnsolvedWord()
        
        override suspend fun isWordExist(word: String) = wordDao.isWordExist(word)
        
        override suspend fun update(word: Word) = wordDao.update(word)
        
        override suspend fun incrementGamesCounter() {
            userStatInteract.incrementGamesCounter()
        }
    }
    
    class Fake(private val valid: Boolean) : MainRepository {
        override suspend fun getUnsolvedWord() = Word(1, "test", false, 0)
        
        override suspend fun isWordExist(word: String) = valid
        
        override suspend fun update(word: Word) {}
        
        override suspend fun incrementGamesCounter() {}
        
    }
}