package com.zhigaras.fiveletters.feature.auth.domain.model

sealed class SignInResult {
    
    object Undefined : SignInResult()
    
    object Success : SignInResult()
    
}