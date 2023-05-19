package com.zhigaras.fiveletters.feature.auth.presentation.signin

import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.di.DispatchersModule
import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldState
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldType
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInState

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
