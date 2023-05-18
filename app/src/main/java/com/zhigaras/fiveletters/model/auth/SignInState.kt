package com.zhigaras.fiveletters.model.auth

data class SignInState(
    val email: String,
    val password: String
) {
    val isCompletelyFilled =
        email.isNotBlank() && password.isNotBlank()
    
    companion object {
        val empty = SignInState(
            email = "",
            password = ""
        )
    }
}


