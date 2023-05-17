package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.menu.UserStat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface UserStatRepository {
    
    suspend fun getUserStatFlow(): Flow<UserStat>
    
    class Base(
        private val wordDao: UserStatDao,
        private val userStat: UserStatInteract.Read
    ) : UserStatRepository {
        
        override suspend fun getUserStatFlow(): Flow<UserStat> {
            val dictionarySize = wordDao.getDictionarySize()
            return combine(
                wordDao.getSolvedWordsCount(),
                userStat.getGamesCount(),
                wordDao.getAverageAttempt()
            ) { words, games, attempts ->
                UserStat.Base(
                    wins = words,
                    progress = words.toFloat() / dictionarySize,
                    games = games,
                    averageAttempts = attempts ?: 0f
                )
            }
        }
    }
}