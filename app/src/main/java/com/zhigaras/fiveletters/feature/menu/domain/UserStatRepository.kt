package com.zhigaras.fiveletters.feature.menu.domain

import com.kpstv.firebase.ValueEventResponse
import com.zhigaras.fiveletters.feature.menu.data.model.UserStatDto
import kotlinx.coroutines.flow.Flow

interface UserStatRepository {
    
    suspend fun getUserStat(): UserStatDto?
    
    suspend fun getUserStatFlow(): Flow<ValueEventResponse>
    
    suspend fun tempPutUserStat() // todo fix with UserStatDto
    
    suspend fun incrementGamesCount()
    
    suspend fun incrementWinsCount()
    
    suspend fun incrementAttempt(lineNumber: Int)
    
}