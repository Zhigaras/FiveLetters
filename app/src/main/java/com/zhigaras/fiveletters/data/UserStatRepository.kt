package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.UserStat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface UserStatRepository : UserStatInteract.RulesIndicationControl {
    
    suspend fun getUserStatFlow(): Flow<UserStat>
    
    class Base(
        private val wordDao: UserStatDao,
        private val userStat: UserStatInteract.Mutable
    ) : UserStatRepository {
        
        override suspend fun getUserStatFlow(): Flow<UserStat> {
            val dictionarySize = wordDao.getDictionarySize()
            return combine(
                wordDao.getSolvedWordsCount(),
                userStat.getGamesCount(),
                wordDao.getAverageAttempt(),
                userStat.wasRulesShown()
            ) { words, games, attempts, wasRulesShown ->
                UserStat.Base(
                    wins = words,
                    progress = words.toFloat() / dictionarySize,
                    games = games,
                    averageAttempts = attempts ?: 0f,
                    wasRulesShown = wasRulesShown
                )
            }
        }
        
        override suspend fun setRulesWasShown() {
            userStat.setRulesWasShown()
        }
    }
}