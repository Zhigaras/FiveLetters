package com.zhigaras.fiveletters.feature.auth.domain.usecases

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.play.core.integrity.model.IntegrityErrorCode.TOO_MANY_REQUESTS
import com.zhigaras.fiveletters.feature.auth.core.CouldNotGetCredentials
import com.zhigaras.fiveletters.feature.auth.core.CouldNotStartOneTapSignIn
import com.zhigaras.fiveletters.feature.auth.core.NetworkException
import com.zhigaras.fiveletters.feature.auth.core.NoGoogleAccountsFound
import com.zhigaras.fiveletters.feature.auth.core.OneTapSignInCanceled
import com.zhigaras.fiveletters.feature.auth.core.TokenNotReceived
import com.zhigaras.fiveletters.feature.auth.core.TooManyOneTapRequests
import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import kotlinx.coroutines.tasks.await

interface SignInWithGoogleUseCase {
    
    suspend fun signIn(
        signWithGoogleLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        signInClient: SignInClient
    )
    
    suspend fun changeGoogleIdToCredential(result: ActivityResult, signInClient: SignInClient)
    
    class Base(
        private val authRepository: AuthRepository
    ) : SignInWithGoogleUseCase {
        
        override suspend fun signIn(
            signWithGoogleLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
            signInClient: SignInClient
        ) {
            try {
                val signInResult =
                    signInClient.beginSignIn(getSignInWithGoogleRequest()).await()
                try {
                    signWithGoogleLauncher.launch(
                        IntentSenderRequest.Builder(signInResult.pendingIntent).build()
                    )
                } catch (e: Exception) {
                    throw CouldNotStartOneTapSignIn()
                }
            } catch (e: ApiException) {
                if (e.statusCode == TOO_MANY_REQUESTS) throw TooManyOneTapRequests()
                throw NoGoogleAccountsFound()
            } catch (e: Exception) {
                throw CouldNotStartOneTapSignIn()
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
        
        private fun getSignInWithGoogleRequest(): BeginSignInRequest {
            return BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(WEB_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
                .setAutoSelectEnabled(true) //TODO check if it needed or not
                .build()
        }
        
        private companion object {
            const val WEB_CLIENT_ID = //TODO move to BuildConfig
                "470265061278-l6757bt5k9q6eh7bls1hs2vs9m1a93si.apps.googleusercontent.com"
        }
    }
}