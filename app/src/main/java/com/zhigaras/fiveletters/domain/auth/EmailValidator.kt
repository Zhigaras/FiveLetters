package com.zhigaras.fiveletters.domain.auth

import android.util.Patterns

interface EmailValidator {
    
    fun validate(email: String): Boolean
    
    class Base : EmailValidator {
        
        override fun validate(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}