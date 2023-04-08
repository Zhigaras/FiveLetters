package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.DatastoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val datastoreManager: DatastoreManager,
    private val dispatchers: DispatchersModule
) : ViewModel(), UsernameInteract {
    
    private val _usernameFlow = MutableStateFlow("")
    val usernameFlow = _usernameFlow.asStateFlow()
    
    override fun onNameChanged(name: String) {
        _usernameFlow.value = name
    }
    
    override fun saveUsername() {
        viewModelScope.launch(dispatchers.io()) {
            datastoreManager.saveUsername(usernameFlow.value)
        }
    }
}

interface UsernameInteract {
    
    fun onNameChanged(name: String)
    
    fun saveUsername()
}