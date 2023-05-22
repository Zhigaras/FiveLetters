package com.zhigaras.fiveletters.feature.play.data

import com.zhigaras.fiveletters.database.MainDao
import com.zhigaras.fiveletters.feature.play.domain.MainRepository
import com.zhigaras.fiveletters.feature.play.domain.UserStatInteract
import com.zhigaras.fiveletters.feature.play.domain.model.Word

class MainRepositoryImpl(
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

class MainRepositoryFake(private val isWordValid: Boolean) : MainRepository {
    override suspend fun getUnsolvedWord() = Word.mock
    
    override suspend fun isWordExist(word: String) = isWordValid
    
    override suspend fun update(word: Word) {}
    
    override suspend fun incrementGamesCounter() {}
    
}