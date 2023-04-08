package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WelcomeViewModel : ViewModel(), UsernameInteract {
    
    private val _usernameFlow = MutableStateFlow("")
    val usernameFlow = _usernameFlow.asStateFlow()
    
    override fun onNameChanged(name: String) {
        _usernameFlow.value = name
    }
    
    override fun saveUsername() {
    
    }
}

interface UsernameInteract {
    
    fun onNameChanged(name: String)
    
    fun saveUsername()
}