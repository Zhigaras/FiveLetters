package com.zhigaras.fiveletters.model.auth

data class AuthState(
    val email: String,
    val password: String,
    val status: AuthProcessStatus
) {
    companion object {
        val empty = AuthState(
            email = "",
            password = "",
            status = AuthProcessStatus.Empty
        )
    }
}
