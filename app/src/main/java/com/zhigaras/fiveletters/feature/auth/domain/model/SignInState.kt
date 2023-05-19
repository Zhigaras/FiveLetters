package com.zhigaras.fiveletters.feature.auth.domain.model

data class SignInState(
    val email: InputFieldState = InputFieldState(),
    val password: InputFieldState = InputFieldState()
) {
    val isCompletelyFilled = email.value.isNotBlank() && password.value.isNotBlank()
    
}