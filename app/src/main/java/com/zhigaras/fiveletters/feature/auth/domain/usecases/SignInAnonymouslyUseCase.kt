package com.zhigaras.fiveletters.feature.auth.domain.usecases

import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository

interface SignInAnonymouslyUseCase {
    
    suspend fun signIn()
    
    class Base(private val authRepository: AuthRepository) : SignInAnonymouslyUseCase {
        
        override suspend fun signIn() {
            authRepository.signInAnonymously()
        }
    }
}