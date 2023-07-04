package com.zhigaras.fiveletters.feature.auth.domain

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    
    suspend fun signUpWithEmailAndPassword(email: String, password: String)
    
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    
    suspend fun signInAnonymously()
    
    suspend fun changeGoogleIdToCredential(token: String)
    
    suspend fun resetPassword(email: String)
    
    fun getCurrentUser(): FirebaseUser? // TODO remove after debugging
    
}