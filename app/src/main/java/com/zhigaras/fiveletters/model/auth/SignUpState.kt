package com.zhigaras.fiveletters.model.auth

data class SignUpState(
    val email: InputFieldState,
    val password: InputFieldState,
    val passwordRepeat: InputFieldState
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
