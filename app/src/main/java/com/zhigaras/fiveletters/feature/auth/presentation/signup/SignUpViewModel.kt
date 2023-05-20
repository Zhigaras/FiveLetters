package com.zhigaras.fiveletters.feature.auth.presentation.signup

import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.di.DispatchersModule
import com.zhigaras.fiveletters.feature.auth.domain.model.ErrorEvent
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldState
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldType
import com.zhigaras.fiveletters.feature.auth.domain.model.SignUpState
import com.zhigaras.fiveletters.feature.auth.domain.usecases.SignUpWithEmailAndPasswordUseCase

class SignUpViewModel(
    private val signUpWithEmailAndPasswordUseCase: SignUpWithEmailAndPasswordUseCase,
    private val dispatchers: DispatchersModule
) : BaseViewModel<SignUpState>(SignUpState()) {
    
    fun onFieldChanged(type: InputFieldType, field: String) {
        state = when (type) {
            InputFieldType.EMAIL -> state.copy(email = InputFieldState(field))
            InputFieldType.PASSWORD -> state.copy(password = InputFieldState(field))
            InputFieldType.REPEAT_PASSWORD -> state.copy(passwordRepeat = InputFieldState(field))
        }
    }
    
    fun signUpWithEmailAndPassword() {
        scopeLaunch(
            context = dispatchers.io(),
            onLoading = { setLoading() },
            onError = { showError(UiText.Resource(it.messageId)); revokeLoading() },
            onFinally = { revokeLoading() }
        ) {
            signUpWithEmailAndPasswordUseCase.signUpWithEmailAndPassword(state).let {
                state = it
            }
        }
    }
    
    fun clearEmail() {
        state = state.copy(email = InputFieldState(""))
    }
    
    private fun setLoading() {
        state = state.copy(isLoading = true)
    }
    
    private fun revokeLoading() {
        state = state.copy(isLoading = false)
    }
    
    private fun showError(message: UiText) {
        state = state.copy(errorEvent = ErrorEvent(message).also { it.trigger() })
    }
    
    fun onConsumeError() {
        state = state.copy(errorEvent = ErrorEvent.CONSUMED)
    }
}