package com.zhigaras.fiveletters.feature.menu.domain.usecases

import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat

interface GetUserStatUseCase {
    
    suspend fun getUserStat(): UserStat
    
    suspend fun tempPutUserStat()
    
    class Base(private val userStatRepository: UserStatRepository) : GetUserStatUseCase {
        
        override suspend fun getUserStat(): UserStat {
            return userStatRepository.getUserStat()?.map() ?: UserStat.initial
        }
        
        override suspend fun tempPutUserStat() {
            userStatRepository.tempPutUserStat()
        }
    }
}