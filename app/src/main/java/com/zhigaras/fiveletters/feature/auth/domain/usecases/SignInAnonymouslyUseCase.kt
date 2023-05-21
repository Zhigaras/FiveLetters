package com.zhigaras.fiveletters.feature.auth.domain.usecases

import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository

interface SignInAnonymouslyUseCase {
    
    suspend fun signInAnonymously()
    
    class Base(private val authRepository: AuthRepository) : SignInAnonymouslyUseCase {
        
        override suspend fun signInAnonymously() {
            authRepository.signInAnonymously()
        }
    }
}