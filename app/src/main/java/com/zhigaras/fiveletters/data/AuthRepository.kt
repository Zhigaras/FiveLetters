package com.zhigaras.fiveletters.data

import com.google.firebase.auth.FirebaseAuth
import com.zhigaras.fiveletters.core.AuthException
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    
    class Base(private val auth: FirebaseAuth) : AuthRepository {
        
        override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
                ?: throw AuthException.RegistrationFailed()
            user.sendEmailVerification()
        }
        
        override suspend fun signInWithEmailAndPassword(email: String, password: String) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!it.isSuccessful) throw AuthException.SigningInFailed()
            }
        }
    }
}