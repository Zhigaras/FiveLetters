package com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.signin

import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.AuthRepository
import com.zhigaras.fiveletters.data.CredentialsValidator
import com.zhigaras.fiveletters.model.auth.InputFieldType
import com.zhigaras.fiveletters.model.auth.InputFieldState
import com.zhigaras.fiveletters.model.auth.SignInState
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.BaseViewModel

class SignInViewModel(
    private val authRepository: AuthRepository, //TODO replace with useCase
    private val credentialsValidator: CredentialsValidator,
    private val dispatchers: DispatchersModule
) : BaseViewModel<SignInState>(SignInState()) {
    
    fun onFieldChanged(type: InputFieldType, field: String) {
        state = when (type) {
            InputFieldType.EMAIL -> state.copy(email = InputFieldState(field))
            InputFieldType.PASSWORD -> state.copy(password = InputFieldState(field))
            else -> state
        }
    }
    
    fun signIn() {
        scopeLaunch(dispatchers.io()) {
            state = credentialsValidator.validateSignIn(state)
//            authRepository.signInWithEmailAndPassword(state.email.value, state.password.value)
        }
    }
    
    fun clearEmail() {
        state = state.copy(email = InputFieldState(""))
    }
}
