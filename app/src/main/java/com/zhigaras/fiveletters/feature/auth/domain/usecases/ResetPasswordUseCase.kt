package com.zhigaras.fiveletters.feature.auth.domain.usecases

import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.ProcessResult
import com.zhigaras.fiveletters.feature.auth.domain.model.ResetPasswordState

interface ResetPasswordUseCase {
    
    suspend fun resetPassword(state: ResetPasswordState): ResetPasswordState
    
    class Base(
        private val authRepository: AuthRepository,
        private val resetPasswordValidator: CredentialsValidator.ResetPasswordEmailValidator
    ) : ResetPasswordUseCase {
        
        override suspend fun resetPassword(state: ResetPasswordState): ResetPasswordState {
            resetPasswordValidator.validate(state).let {
                if (it.isValid) {
                    authRepository.resetPassword(state.email.value)
                    return it.copy(processResult = ProcessResult.Success)
                } else return it
            }
        }
    }
}