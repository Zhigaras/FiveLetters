package com.zhigaras.fiveletters.feature.auth.domain

interface AuthRepository {
    
    suspend fun signUpWithEmailAndPassword(email: String, password: String)
    
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    
    suspend fun signInAnonymously()
    
    suspend fun changeGoogleIdToCredential(token: String)
    
    suspend fun resetPassword(email: String)
    
}