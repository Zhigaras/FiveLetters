package com.zhigaras.fiveletters.feature.auth.domain.usecases

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.zhigaras.fiveletters.core.CouldNotGetCredentials
import com.zhigaras.fiveletters.core.CouldNotStartOneTapSignIn
import com.zhigaras.fiveletters.core.NetworkException
import com.zhigaras.fiveletters.core.NoGoogleAccountsFound
import com.zhigaras.fiveletters.core.OneTapSignInCanceled
import com.zhigaras.fiveletters.core.TokenNotReceived
import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import kotlinx.coroutines.tasks.await

interface SignWithGoogleUseCase {
    
    suspend fun signInWithGoogle(
        signWithGoogleLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        signInClient: SignInClient
    )
    
    suspend fun changeGoogleIdToCredential(result: ActivityResult, signInClient: SignInClient)
    
    class Base(
        private val authRepository: AuthRepository
    ) : SignWithGoogleUseCase {
        
        override suspend fun signInWithGoogle(
            signWithGoogleLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
            signInClient: SignInClient
        ) {
            try {
                val signInResult =
                    signInClient.beginSignIn(authRepository.getSignInWithGoogleRequest()).await()
                try {
                    signWithGoogleLauncher.launch(
                        IntentSenderRequest.Builder(signInResult.pendingIntent).build()
                    )
                } catch (e: Exception) {
                    throw CouldNotStartOneTapSignIn()
                }
            } catch (e: Exception) {
                throw NoGoogleAccountsFound()
            }
        }
        
        override suspend fun changeGoogleIdToCredential(
            result: ActivityResult,
            signInClient: SignInClient
        ) {
            try {
                val token = signInClient.getSignInCredentialFromIntent(result.data).googleIdToken
                if (token != null) {
                    authRepository.changeGoogleIdToCredential(token)
                } else {
                    throw TokenNotReceived()
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    CommonStatusCodes.NETWORK_ERROR -> throw NetworkException()
                    CommonStatusCodes.CANCELED -> throw OneTapSignInCanceled()
                    else -> throw CouldNotGetCredentials()
                }
            }
        }
    }
}