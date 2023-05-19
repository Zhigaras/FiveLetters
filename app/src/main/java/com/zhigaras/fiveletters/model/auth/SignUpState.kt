package com.zhigaras.fiveletters.model.auth

import com.zhigaras.fiveletters.core.presentation.StateEventWithContent
import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.core.presentation.consumed

data class SignUpState(
    val email: InputFieldState,
    val password: InputFieldState,
    val passwordRepeat: InputFieldState,
    val isLoading: Boolean = false,
    val errorEvent: StateEventWithContent<UiText> = consumed()
) {
    val isCompletelyFilled =
        email.value.isNotBlank() && password.value.isNotBlank() && passwordRepeat.value.isNotBlank()
    
    companion object {
        val empty = SignUpState(
            email = InputFieldState(),
            password = InputFieldState(),
            passwordRepeat = InputFieldState()
        )
    }
}
