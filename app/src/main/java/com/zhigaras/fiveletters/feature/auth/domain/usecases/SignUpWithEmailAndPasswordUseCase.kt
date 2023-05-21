package com.zhigaras.fiveletters.feature.auth.domain.usecases

import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.ProcessResult
import com.zhigaras.fiveletters.feature.auth.domain.model.SignUpState

interface SignUpWithEmailAndPasswordUseCase {
    
    suspend fun signUp(state: SignUpState): SignUpState
    
    class Base(
        private val authRepository: AuthRepository,
        private val signUpValidator: CredentialsValidator.SignUpValidator
    ) : SignUpWithEmailAndPasswordUseCase {
        
        override suspend fun signUp(state: SignUpState): SignUpState {
            signUpValidator.validate(state).let {
                if (it.isValid) {
                    authRepository.signUpWithEmailAndPassword(
                        state.email.value,
                        state.password.value
                    )
                    return it.copy(processResult = ProcessResult.Success)
                } else return it
            }
        }
    }
}