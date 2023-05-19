package com.zhigaras.fiveletters.feature.menu.domain.usecases

import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat
import kotlinx.coroutines.flow.Flow

interface GetUserStatUseCase {
    
    suspend fun getUserStatFlow(): Flow<UserStat>
    
    class Base(private val userStatRepository: UserStatRepository) : GetUserStatUseCase {
        
        override suspend fun getUserStatFlow(): Flow<UserStat> {
            return userStatRepository.getUserStatFlow()
        }
    }
}