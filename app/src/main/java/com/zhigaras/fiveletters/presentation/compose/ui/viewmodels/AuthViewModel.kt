package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.zhigaras.fiveletters.domain.auth.AuthStateController
import com.zhigaras.fiveletters.model.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    private val authStateController: AuthStateController
): ViewModel(), AuthInteract {
    
    private val _authStateFlow = MutableStateFlow(AuthState.empty)
    val authStateFlow = _authStateFlow.asStateFlow()
    
    override fun onEmailChanged(email: String) {
        authStateController.updateEmail(_authStateFlow.value, email).let {
            _authStateFlow.value = it
        }
    }
    
    override fun onPasswordChanged(password: String) {
        authStateController.updatePassword(_authStateFlow.value, password).let {
            _authStateFlow.value = it
        }
    }
}

interface AuthInteract {
    
    fun onEmailChanged(email: String)
    
    fun onPasswordChanged(password: String)
}
