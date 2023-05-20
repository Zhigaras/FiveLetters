package com.zhigaras.fiveletters.feature.auth.domain.model

import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.core.presentation.compose.StateEventWithContent
import com.zhigaras.fiveletters.core.presentation.compose.consumed

data class SignInState(
    val email: InputFieldState = InputFieldState(),
    val password: InputFieldState = InputFieldState(),
    val isLoading: Boolean = false,
    val isValid: Boolean = true,
    val errorEvent: StateEventWithContent<UiText> = consumed(),
    val signUpResult: SignUpResult = SignUpResult.Undefined
) {
    val isCompletelyFilled = email.value.isNotBlank() && password.value.isNotBlank()
    
}