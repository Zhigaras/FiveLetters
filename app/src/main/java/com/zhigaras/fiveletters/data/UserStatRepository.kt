package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.UserStat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface UserStatRepository : DatastoreManager {
    
    suspend fun getUserStatFlow(): Flow<UserStat>
    
    
    class Base(
        private val wordDao: WordDao,
        private val userStat: DatastoreManager.UserStat.Read
    ) : UserStatRepository {
        
        override suspend fun getUserStatFlow(): Flow<UserStat> {
            val dictionarySize = wordDao.getDictionarySize()
            return combine(
                wordDao.getSolvedWordsCount(),
                userStat.getGamesCount(),
                wordDao.getAverageAttempt()
            ) { words, games, attempts ->
                UserStat(
                    wins = words,
                    progress = words.toFloat() / dictionarySize,
                    games = games,
                    averageAttempts = attempts ?: 0f
                )
            }
        }
    }
}