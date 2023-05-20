package com.zhigaras.fiveletters.feature.auth.domain

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    
    suspend fun changeGoogleIdToCredential(token: String)
    
    fun getCurrentUser(): FirebaseUser? // TODO remove after debugging
    
}