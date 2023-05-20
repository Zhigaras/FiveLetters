package com.zhigaras.fiveletters.feature.auth.domain.usecases

import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInState
import com.zhigaras.fiveletters.feature.auth.domain.model.SignUpResult

interface SignInWithEmailAndPasswordUseCase {
    
    suspend fun signInWithEmailAndPassword(state: SignInState): SignInState
    
    class Base(
        private val authRepository: AuthRepository,
        private val signInValidator: CredentialsValidator.SignInValidator
    ) : SignInWithEmailAndPasswordUseCase {
        
        override suspend fun signInWithEmailAndPassword(state: SignInState): SignInState {
            signInValidator.validate(state).let {
                if (it.isValid) {
                    authRepository.signInWithEmailAndPassword(
                        state.email.value,
                        state.password.value
                    )
                    return it.copy(signUpResult = SignUpResult.Success)
                } else return it
            }
        }
    }
}
