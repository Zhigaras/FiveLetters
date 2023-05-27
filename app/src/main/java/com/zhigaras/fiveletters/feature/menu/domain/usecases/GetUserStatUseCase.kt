package com.zhigaras.fiveletters.feature.menu.domain.usecases

import com.kpstv.firebase.ValueEventResponse
import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat
import kotlinx.coroutines.flow.Flow

interface GetUserStatUseCase {
    
    suspend fun getUserStat(): UserStat
    
    suspend fun getUsrStatFlow(): Flow<ValueEventResponse>
    
    class Base(private val userStatRepository: UserStatRepository) : GetUserStatUseCase {
        
        override suspend fun getUserStat(): UserStat {
            return userStatRepository.getUserStat()?.map() ?: UserStat.Base.initial
        }
        
        override suspend fun getUsrStatFlow(): Flow<ValueEventResponse> {
            return userStatRepository.getUserStatFlow()
        }
    }
}