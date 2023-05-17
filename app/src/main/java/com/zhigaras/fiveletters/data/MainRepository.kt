package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.play.Word

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
    
    class Fake(private val isWordValid: Boolean) : MainRepository {
        override suspend fun getUnsolvedWord() = Word.mock
        
        override suspend fun isWordExist(word: String) = isWordValid
        
        override suspend fun update(word: Word) {}
        
        override suspend fun incrementGamesCounter() {}
        
    }
}