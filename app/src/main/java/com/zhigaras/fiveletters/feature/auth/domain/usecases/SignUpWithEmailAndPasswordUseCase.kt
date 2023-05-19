package com.zhigaras.fiveletters.feature.auth.domain.usecases

import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.SignUpState

interface SignUpWithEmailAndPasswordUseCase {
    
    fun signUpWithEmailAndPassword(state: SignUpState): SignUpState
    
    class Base(
        private val credentialsValidator: CredentialsValidator,
        private val authRepository: AuthRepository
    ) : SignUpWithEmailAndPasswordUseCase {
        
        override fun signUpWithEmailAndPassword(state: SignUpState): SignUpState {
            return credentialsValidator.validateSignUp(state)
//            authRepository.createUserWithEmailAndPassword(state.email.value, state.password.value)
        
        }
    }
}