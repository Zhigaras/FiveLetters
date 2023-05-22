package com.zhigaras.fiveletters.feature.menu.domain

import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat
import kotlinx.coroutines.flow.Flow

interface UserStatRepository {
    
    suspend fun getUserStatFlow(): Flow<UserStat>
    
}