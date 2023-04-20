package com.zhigaras.fiveletters.data

interface UsernameInteract {
    
    suspend fun saveUsername(name: String)
    
    suspend fun readUsername(): String
    
}