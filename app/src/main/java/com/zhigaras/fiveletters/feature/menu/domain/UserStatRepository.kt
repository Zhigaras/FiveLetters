package com.zhigaras.fiveletters.feature.menu.domain

import com.zhigaras.fiveletters.feature.menu.data.model.UserStatDto

interface UserStatRepository {
    
    suspend fun getUserStat(): UserStatDto?
    
    suspend fun tempPutUserStat() // todo fix with UserStatDto
    
    suspend fun incrementGamesCount()
    
    suspend fun incrementWinsCount()
    
    suspend fun incrementAttempt(lineNumber: Int)
    
}