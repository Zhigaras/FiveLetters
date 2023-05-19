package com.zhigaras.fiveletters.feature.auth.domain

import com.zhigaras.fiveletters.feature.auth.domain.model.SignInState
import com.zhigaras.fiveletters.feature.auth.domain.model.SignUpState

interface CredentialsValidator {
    
    fun validateSignIn(state: SignInState): SignInState
    
    fun validateSignUp(state: SignUpState): SignUpState
    
}