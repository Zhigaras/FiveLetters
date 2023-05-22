package com.zhigaras.fiveletters.feature.auth.data

import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.zhigaras.fiveletters.feature.auth.core.EmailIsUsed
import com.zhigaras.fiveletters.feature.auth.core.InvalidCredentials
import com.zhigaras.fiveletters.feature.auth.core.InvalidUser
import com.zhigaras.fiveletters.feature.auth.core.NoSuchUser
import com.zhigaras.fiveletters.feature.auth.core.RegistrationFailed
import com.zhigaras.fiveletters.feature.auth.core.SigningInFailed
import com.zhigaras.fiveletters.feature.auth.core.TooManyRequests
import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {
    
    override suspend fun signUpWithEmailAndPassword(email: String, password: String) {
        try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
            user?.sendEmailVerification()
        } catch (e: FirebaseAuthUserCollisionException) {
            throw EmailIsUsed()
        } catch (e: Exception) {
            throw RegistrationFailed()
        }
    }
    
    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).await().user
        } catch (e: FirebaseAuthInvalidUserException) {
            throw InvalidUser()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw InvalidCredentials()
        } catch (e: FirebaseTooManyRequestsException) {
            throw TooManyRequests()
        } catch (e: Exception) {
            throw SigningInFailed()
        }
    }
    
    override suspend fun signInAnonymously() {
        try {
            auth.signInAnonymously().await().user
        } catch (e: Exception) {
            throw SigningInFailed()
        }
    }
    
    override suspend fun changeGoogleIdToCredential(token: String) {
        val firebaseCredentials = GoogleAuthProvider.getCredential(token, null)
        try {
            auth.signInWithCredential(firebaseCredentials).await()
        } catch (e: Exception) {
            throw SigningInFailed()
        }
    }
    
    override suspend fun resetPassword(email: String) {
        try {
            auth.sendPasswordResetEmail(email).await()
        } catch (e: Exception) {
            throw NoSuchUser()
        }
    }
    
    override fun getCurrentUser(): FirebaseUser? = auth.currentUser //TODO move to needed layer
    
}