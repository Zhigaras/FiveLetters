package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.UsernameRepository
import com.zhigaras.fiveletters.model.Username
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val usernameRepository: UsernameRepository,
    private val dispatchers: DispatchersModule
): ViewModel(), UsernameInteract.Check {
    
    private val _userFlow: MutableStateFlow<Username> = MutableStateFlow(Username.NotLoadedYet())
    val userFlow = _userFlow.asStateFlow()
    
    override fun checkUsername() {
        viewModelScope.launch(dispatchers.io()) {
            _userFlow.value = Username.NotLoadedYet()
            _userFlow.value = usernameRepository.readUsername()
        }
    }
}