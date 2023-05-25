package com.zhigaras.fiveletters.feature.menu.domain

import com.zhigaras.fiveletters.feature.menu.data.model.UserStatDto

interface UserStatRepository {
    
    suspend fun getUserStatFlow(userId: String): UserStatDto?
    
    suspend fun tempPutUserStat(userId: String) // todo fix with UserStatDto
    
}