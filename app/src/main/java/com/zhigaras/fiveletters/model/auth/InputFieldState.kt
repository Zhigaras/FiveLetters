package com.zhigaras.fiveletters.model.auth

import com.zhigaras.fiveletters.R

data class InputFieldState(
    val value: String = "",
    val validity: InputFieldValidity = InputFieldValidity.Valid
) {
    val isInvalid = validity is InputFieldValidity.Invalid
}

abstract class InputFieldValidity {
    
    object Valid : InputFieldValidity()
    
    abstract class Invalid : InputFieldValidity() {
        
        abstract val error: Int
        
        class InvalidEmail : Invalid() {
            override val error = R.string.email_is_invalid
        }
        
        class InvalidPassword : Invalid() {
            override val error = R.string.password_is_invalid
        }
        
        class PasswordMismatch : Invalid() {
            override val error = R.string.passport_mismatch
        }
    }
}