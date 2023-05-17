package com.zhigaras.fiveletters.model

data class AuthState(
    val email: String,
    val password: String,
    val status: AuthProcessStatus
) {
    companion object {
        val EMPTY = AuthState(
            email = "",
            password = "",
            status = AuthProcessStatus.Empty
        )
    }
}
