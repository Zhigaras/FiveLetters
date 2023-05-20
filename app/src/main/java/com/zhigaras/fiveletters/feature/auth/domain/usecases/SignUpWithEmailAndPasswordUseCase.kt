package com.zhigaras.fiveletters.feature.auth.domain.usecases

import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInResult
import com.zhigaras.fiveletters.feature.auth.domain.model.SignUpState

interface SignUpWithEmailAndPasswordUseCase {
    
    suspend fun signUpWithEmailAndPassword(state: SignUpState): SignUpState
    
    class Base(
        private val authRepository: AuthRepository,
        private val signUpValidator: CredentialsValidator.SignUpValidator
    ) : SignUpWithEmailAndPasswordUseCase {
        
        override suspend fun signUpWithEmailAndPassword(state: SignUpState): SignUpState {
            signUpValidator.validate(state).let {
                if (it.isValid) {
                    authRepository.createUserWithEmailAndPassword(
                        state.email.value,
                        state.password.value
                    )
                    return it.copy(signInResult = SignInResult.Success)
                } else return it
            }
        }
    }
}