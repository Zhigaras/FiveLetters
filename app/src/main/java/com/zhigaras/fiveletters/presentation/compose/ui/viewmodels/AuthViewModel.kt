package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import com.zhigaras.fiveletters.model.auth.AuthState

class AuthViewModel : BaseViewModel<AuthState>(AuthState.empty), AuthInteract {
    
    override fun onEmailChanged(email: String) {
        state = state.copy(email = email)
    }
    
    override fun onPasswordChanged(password: String) {
        state = state.copy(password = password)
    }
    
    override fun onPasswordRepeatChanged(passwordRepeat: String) {
        state = state.copy(passwordRepeat = passwordRepeat)
    }
}

interface AuthInteract {
    
    fun onEmailChanged(email: String)
    
    fun onPasswordChanged(password: String)
    
    fun onPasswordRepeatChanged(passwordRepeat: String)
}
