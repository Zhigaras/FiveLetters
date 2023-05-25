package com.zhigaras.fiveletters.feature.menu.domain.usecases

import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat

interface GetUserStatUseCase {
    
    suspend fun getUserStat(userId: String): UserStat
    
    class Base(private val userStatRepository: UserStatRepository) : GetUserStatUseCase {
        
        override suspend fun getUserStat(userId: String): UserStat {
            return userStatRepository.getUserStatFlow(userId)?.map() ?: UserStat.initial
        }
    }
}