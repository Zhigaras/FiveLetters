package com.zhigaras.fiveletters.model.auth

sealed class SignUpResult {
    
    object Undefined : SignUpResult()
    
    object Success : SignUpResult()
    
}