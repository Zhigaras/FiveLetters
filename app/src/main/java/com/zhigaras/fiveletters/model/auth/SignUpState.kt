package com.zhigaras.fiveletters.model.auth

data class SignUpState(
    val email: String,
    val password: String,
    val passwordRepeat: String
) {
    val isCompletelyFilled =
        email.isNotBlank() && password.isNotBlank() && passwordRepeat.isNotBlank()
    
    companion object {
        val empty = SignUpState(
            email = "",
            password = "",
            passwordRepeat = ""
        )
    }
}
