package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(

): ViewModel(), AuthInteract {
    
    private val _emailFlow = MutableStateFlow("")
    val emailFlow = _emailFlow.asStateFlow()
    
    private val _passwordFlow = MutableStateFlow("")
    val passwordFlow = _passwordFlow.asStateFlow()
    
    override fun onNameChanged(name: String) {
        _emailFlow.value = name
    }
    
    override fun onPasswordChanged(password: String) {
        _passwordFlow.value = password
    }
    
    override fun saveUsername() {
    
    }
}

interface AuthInteract {
    
    fun onNameChanged(name: String)
    
    fun saveUsername()
    
    fun onPasswordChanged(password: String)
}
