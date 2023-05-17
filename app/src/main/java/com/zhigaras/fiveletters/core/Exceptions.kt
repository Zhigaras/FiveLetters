package com.zhigaras.fiveletters.core

import com.zhigaras.fiveletters.R

abstract class AuthException : Exception() {
    
    abstract val messageId: Int
    
    class RegistrationFailed : AuthException() {
        override val messageId = R.string.registration_failed
    }
    
    class SigningInFailed : AuthException() {
        override val messageId: Int = R.string.signing_in_failed
        
    }
}