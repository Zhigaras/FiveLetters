package com.zhigaras.fiveletters.model.auth

data class AuthState(
    val email: String,
    val password: String,
    val passwordRepeat: String,
//    val status: AuthProcessStatus
) {
    val isCompletelyFilled =
        email.isNotBlank() && password.isNotBlank() && passwordRepeat.isNotBlank()
    
    companion object {
        val empty = AuthState(
            email = "",
            password = "",
            passwordRepeat = "",
//            status = AuthProcessStatus.Empty
        )
    }
}
