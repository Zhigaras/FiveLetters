package com.zhigaras.fiveletters.presentation.compose.ui.screens.auth

import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.AuthRepository
import com.zhigaras.fiveletters.domain.auth.InputFieldType
import com.zhigaras.fiveletters.model.auth.SignInState
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.BaseViewModel

class SignInViewModel(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatchersModule
) : BaseViewModel<SignInState>(SignInState.empty) {
    
    fun onFieldChanged(type: InputFieldType, field: String) {
        state = when (type) {
            InputFieldType.EMAIL -> state.copy(email = field)
            InputFieldType.PASSWORD -> state.copy(password = field)
            else -> state
        }
    }
    
    fun signIn() {
        scopeLaunch(dispatchers.io()) {
            authRepository.signInWithEmailAndPassword(state.email, state.password)
        }
    }
}
