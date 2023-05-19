package com.zhigaras.fiveletters.model.auth

import com.zhigaras.fiveletters.core.presentation.StateEventWithContent
import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.core.presentation.consumed

data class SignUpState(
    val email: InputFieldState = InputFieldState(),
    val password: InputFieldState = InputFieldState(),
    val passwordRepeat: InputFieldState = InputFieldState(),
    val isLoading: Boolean = false,
    val errorEvent: StateEventWithContent<UiText> = consumed(),
    val signUpResult: SignUpResult = SignUpResult.Undefined
) {
    val isCompletelyFilled =
        email.value.isNotBlank() && password.value.isNotBlank() && passwordRepeat.value.isNotBlank()
    
}
