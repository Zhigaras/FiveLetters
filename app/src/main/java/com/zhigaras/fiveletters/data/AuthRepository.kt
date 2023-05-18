package com.zhigaras.fiveletters.data

import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.zhigaras.fiveletters.core.AuthException
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    
    suspend fun getSignInWithGoogleRequest(): BeginSignInRequest
    
    suspend fun changeGoogleIdToCredential(token: String)
    
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
            auth.signInWithCredential(firebaseCredentials).addOnCompleteListener {
                if (it.isSuccessful) Log.d("aaa", "success")
                else if (it.isSuccessful) Log.d("aaa", "error")
                
            }
        }
        
        private companion object {
            const val WEB_CLIENT_ID =
                "470265061278-l6757bt5k9q6eh7bls1hs2vs9m1a93si.apps.googleusercontent.com"
        }
    }
}