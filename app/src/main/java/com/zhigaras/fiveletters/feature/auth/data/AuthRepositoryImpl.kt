package com.zhigaras.fiveletters.feature.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.zhigaras.fiveletters.core.InvalidCredentials
import com.zhigaras.fiveletters.core.InvalidUser
import com.zhigaras.fiveletters.core.RegistrationFailed
import com.zhigaras.fiveletters.core.SigningInFailed
import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {
    
    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        val user = auth.createUserWithEmailAndPassword(email, password).await().user
            ?: throw RegistrationFailed()
        user.sendEmailVerification()
    }
    
    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).await().user
        } catch (e: FirebaseAuthInvalidUserException) {
            throw InvalidUser()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw InvalidCredentials()
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
    
    override fun getCurrentUser(): FirebaseUser? = auth.currentUser //TODO move to needed layer
    
}