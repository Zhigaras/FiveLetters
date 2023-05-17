package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.AuthRepository
import com.zhigaras.fiveletters.model.auth.AuthState

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatchersModule
) : BaseViewModel<AuthState>(AuthState.empty) {
    
    fun onEmailChanged(email: String) {
        state = state.copy(email = email)
    }
    
    fun onPasswordChanged(password: String) {
        state = state.copy(password = password)
    }
    
    fun onPasswordRepeatChanged(passwordRepeat: String) {
        state = state.copy(passwordRepeat = passwordRepeat)
    }
    
    fun registerWithEmailAndPassword() {
        scopeLaunch(
            context = dispatchers.io()
        ) {
            authRepository.createUserWithEmailAndPassword(state.email, state.password)
        }
    }
    
    fun signIn() {
        scopeLaunch(dispatchers.io()) {
            authRepository.signInWithEmailAndPassword(state.email, state.password)
        }
    }
}
