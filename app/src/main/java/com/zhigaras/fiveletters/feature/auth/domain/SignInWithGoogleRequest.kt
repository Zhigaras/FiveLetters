package com.zhigaras.fiveletters.feature.auth.domain

import com.google.android.gms.auth.api.identity.BeginSignInRequest

interface SignInWithGoogleRequest {
    
    fun getRequest(): BeginSignInRequest
    
    class Base: SignInWithGoogleRequest {
    
        override fun getRequest(): BeginSignInRequest {
            return BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(WEB_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
                .setAutoSelectEnabled(true)
                .build()
        }
        private companion object {
            const val WEB_CLIENT_ID = //TODO move to BuildConfig
                "470265061278-l6757bt5k9q6eh7bls1hs2vs9m1a93si.apps.googleusercontent.com"
        }
    }
}