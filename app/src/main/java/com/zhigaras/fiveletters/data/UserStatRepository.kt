package com.zhigaras.fiveletters.data

import kotlinx.coroutines.flow.Flow

interface UserStatRepository {
    
    suspend fun getDictionarySize(): Int
    
    fun getSolvedWordsCount(): Flow<Int>
    
    
    class Base(private val wordDao: WordDao) : UserStatRepository {
        
        override suspend fun getDictionarySize(): Int =
            wordDao.getDictionarySize()
        
        override fun getSolvedWordsCount(): Flow<Int> {
            return wordDao.getSolvedWordsCount()
        }
    }
}