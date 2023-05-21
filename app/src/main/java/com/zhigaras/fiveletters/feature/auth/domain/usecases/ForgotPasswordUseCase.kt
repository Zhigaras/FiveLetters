package com.zhigaras.fiveletters.feature.auth.domain.usecases

import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository

interface ForgotPasswordUseCase {
    
    suspend fun resetPassword(email: String)
    
    class Base(private val authRepository: AuthRepository) : ForgotPasswordUseCase {
        
        override suspend fun resetPassword(email: String) {
            authRepository.resetPassword(email)
        }
    }
}