package com.zhigaras.fiveletters.feature.auth.domain.model

data class SignUpState(
    val email: InputFieldState = InputFieldState(),
    val password: InputFieldState = InputFieldState(),
    val passwordRepeat: InputFieldState = InputFieldState(),
    val isLoading: Boolean = false,
    val isValid: Boolean = true,
    val errorEvent: ErrorEvent = ErrorEvent.CONSUMED,
    val signInResult: SignInResult = SignInResult.Undefined
) {
    val isCompletelyFilled =
        email.value.isNotBlank() && password.value.isNotBlank() && passwordRepeat.value.isNotBlank()
    
}
