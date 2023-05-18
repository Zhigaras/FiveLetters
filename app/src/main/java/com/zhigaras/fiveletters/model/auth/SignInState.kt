package com.zhigaras.fiveletters.model.auth

data class SignInState(
    val email: InputFieldState,
    val password: InputFieldState
) {
    val isCompletelyFilled = email.value.isNotBlank() && password.value.isNotBlank()
    
    companion object {
        val empty = SignInState(
            email = InputFieldState(),
            password = InputFieldState()
        )
    }
}