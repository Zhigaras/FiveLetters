package com.zhigaras.fiveletters.feature.menu.domain.usecases

import com.zhigaras.fiveletters.feature.menu.domain.UserRepository
import com.zhigaras.fiveletters.feature.menu.domain.model.UserEntity

interface GetCurrentUserUseCase { //todo remove if not needed
    
    suspend fun getUser(): UserEntity
    
    class Base(private val userRepository: UserRepository) : GetCurrentUserUseCase {
        
        override suspend fun getUser(): UserEntity {
            val currentUser = userRepository.getCurrentUser()
            return UserEntity(
                id = currentUser?.uid ?: "sdfsf",
                name = currentUser?.displayName ?: "seff",
                email = currentUser?.email ?: "a;kgjadfk" //TODO delete hardcode
            )
        }
    }
}