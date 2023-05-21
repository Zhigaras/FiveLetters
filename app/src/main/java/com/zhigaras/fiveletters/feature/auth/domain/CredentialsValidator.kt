package com.zhigaras.fiveletters.feature.auth.domain

import com.zhigaras.fiveletters.feature.auth.domain.model.ResetPasswordState
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInState
import com.zhigaras.fiveletters.feature.auth.domain.model.SignUpState

interface CredentialsValidator {
    
    interface SignInValidator : CredentialsValidator {
        
        fun validate(state: SignInState): SignInState
    }
    
    interface SignUpValidator : CredentialsValidator {
        
        fun validate(state: SignUpState): SignUpState
        
    }
    
    interface ResetPasswordEmailValidator : CredentialsValidator {
        
        fun validate(state: ResetPasswordState): ResetPasswordState
    }
    
}