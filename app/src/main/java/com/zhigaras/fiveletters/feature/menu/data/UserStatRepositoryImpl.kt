package com.zhigaras.fiveletters.feature.menu.data

import com.zhigaras.fiveletters.database.UserStatDao
import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat
import com.zhigaras.fiveletters.feature.play.domain.UserStatInteract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class UserStatRepositoryImpl(
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