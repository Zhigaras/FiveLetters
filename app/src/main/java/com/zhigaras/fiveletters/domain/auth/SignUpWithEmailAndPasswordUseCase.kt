package com.zhigaras.fiveletters.domain.auth

import com.zhigaras.fiveletters.data.AuthRepository
import com.zhigaras.fiveletters.data.CredentialsValidator
import com.zhigaras.fiveletters.model.auth.SignUpState

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