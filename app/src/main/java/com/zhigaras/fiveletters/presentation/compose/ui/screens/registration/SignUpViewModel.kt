package com.zhigaras.fiveletters.presentation.compose.ui.screens.registration

import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.AuthRepository
import com.zhigaras.fiveletters.domain.auth.InputFieldType
import com.zhigaras.fiveletters.model.auth.SignUpState
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.BaseViewModel

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatchersModule
) : BaseViewModel<SignUpState>(SignUpState.empty) {
    
    fun onFieldChanged(type: InputFieldType, field: String) {
        state = when (type) {
            InputFieldType.EMAIL -> state.copy(email = field)
            InputFieldType.PASSWORD -> state.copy(password = field)
            InputFieldType.REPEAT_PASSWORD -> state.copy(passwordRepeat = field)
        }
    }
    
    fun signUpWithEmailAndPassword() {
        scopeLaunch(
            context = dispatchers.io()
        ) {
            authRepository.createUserWithEmailAndPassword(state.email, state.password)
        }
    }
    
    fun signUpWithGoogle() {
    
    }
}