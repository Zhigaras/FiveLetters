package com.zhigaras.fiveletters.feature.auth.domain.model

import com.zhigaras.fiveletters.core.presentation.UiText

data class SignInState(
    val email: InputFieldState = InputFieldState(),
    val password: InputFieldState = InputFieldState(),
    val isLoading: Boolean = false,
    val isValid: Boolean = true,
    val errorEvent: ErrorEvent = ErrorEvent.CONSUMED,
    val signInResult: SignInResult = SignInResult.Undefined
) {
    val isCompletelyFilled = email.value.isNotBlank() && password.value.isNotBlank()
    
}

class ErrorEvent(val message: UiText) {
    var isVisible = false
        private set
    
    fun trigger() {
        isVisible = true
    }
    
    companion object {
        val CONSUMED = ErrorEvent(UiText.Empty)
    }
}