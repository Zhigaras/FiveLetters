package com.zhigaras.fiveletters.model

sealed class AuthProcessStatus {
    
    object Empty : AuthProcessStatus()
    
    object NotComplete : AuthProcessStatus()
    
    object Complete : AuthProcessStatus()
    
    object Valid : AuthProcessStatus()
    
    object InvalidEmail : AuthProcessStatus()
    
    object Success : AuthProcessStatus()
    
    object Failed : AuthProcessStatus()
    
}