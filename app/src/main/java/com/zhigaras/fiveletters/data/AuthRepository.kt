package com.zhigaras.fiveletters.data

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.zhigaras.fiveletters.core.RegistrationFailed
import com.zhigaras.fiveletters.core.SigningInFailed
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    
    suspend fun getSignInWithGoogleRequest(): BeginSignInRequest
    
    suspend fun changeGoogleIdToCredential(token: String)
    
    fun getCurrentUser(): FirebaseUser?
    
    class Base(private val auth: FirebaseAuth) : AuthRepository {
        
        override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
                ?: throw RegistrationFailed()
            user.sendEmailVerification()
        }
        
        override suspend fun signInWithEmailAndPassword(email: String, password: String) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!it.isSuccessful) throw SigningInFailed()
            }
        }
        
        override suspend fun getSignInWithGoogleRequest(): BeginSignInRequest {
            return BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(WEB_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            ).build()
        }
        
        override suspend fun changeGoogleIdToCredential(token: String) {
            val firebaseCredentials = GoogleAuthProvider.getCredential(token, null)
            try {
                auth.signInWithCredential(firebaseCredentials).await()
            } catch (e: Exception) {
                throw SigningInFailed()
            }
        }
        
        override fun getCurrentUser(): FirebaseUser? = auth.currentUser //TODO move to needed layer
        
        private companion object {
            const val WEB_CLIENT_ID = //TODO move to BuildConfig
                "470265061278-l6757bt5k9q6eh7bls1hs2vs9m1a93si.apps.googleusercontent.com"
        }
    }
}