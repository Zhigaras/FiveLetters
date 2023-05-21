package com.zhigaras.fiveletters.feature.auth.domain.model

sealed class ProcessResult {
    
    object Undefined : ProcessResult()
    
    object Success : ProcessResult()
    
}