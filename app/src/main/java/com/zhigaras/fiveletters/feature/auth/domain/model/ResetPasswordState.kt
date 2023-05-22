package com.zhigaras.fiveletters.feature.auth.domain.model

data class ResetPasswordState(
    val email: InputFieldState = InputFieldState(),
    val isLoading: Boolean = false,
    val isValid: Boolean = true,
    val errorEvent: ErrorEvent = ErrorEvent.CONSUMED,
    val processResult: ProcessResult = ProcessResult.Undefined
) {
    val isCompletelyFilled = email.value.isNotBlank()
}
