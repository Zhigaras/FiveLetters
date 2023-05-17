package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.AuthProcessStatus
import com.zhigaras.fiveletters.model.AuthState

interface AuthStateController {
    
    fun updateEmail(state: AuthState, email: String): AuthState
    
    fun updatePassword(state: AuthState, password: String): AuthState
    
    class Base : AuthStateController {
        
        override fun updateEmail(state: AuthState, email: String): AuthState {
            val status = if (state.password.isBlank() || email.isBlank()) {
                AuthProcessStatus.NotComplete
            } else {
                AuthProcessStatus.Complete
            }
            return state.copy(email = email, status = status)
        }
        
        override fun updatePassword(state: AuthState, password: String): AuthState {
            val status = if (state.email.isBlank() || password.isBlank()) {
                AuthProcessStatus.NotComplete
            } else {
                AuthProcessStatus.Complete
            }
            return state.copy(password = password, status = status)
        }
    }
}