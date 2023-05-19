package com.zhigaras.fiveletters.feature.auth.domain.model

sealed class SignUpResult {
    
    object Undefined : SignUpResult()
    
    object Success : SignUpResult()
    
}