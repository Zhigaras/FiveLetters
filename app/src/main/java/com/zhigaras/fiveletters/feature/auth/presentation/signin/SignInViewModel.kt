package com.zhigaras.fiveletters.feature.auth.presentation.signin

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.di.DispatchersModule
import com.zhigaras.fiveletters.feature.auth.domain.model.ErrorEvent
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldState
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldType
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInResult
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInState
import com.zhigaras.fiveletters.feature.auth.domain.usecases.SignInAnonymouslyUseCase
import com.zhigaras.fiveletters.feature.auth.domain.usecases.SignInWithEmailAndPasswordUseCase
import com.zhigaras.fiveletters.feature.auth.domain.usecases.SignInWithGoogleUseCase

class SignInViewModel(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase,
//    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val dispatchers: DispatchersModule
) : BaseViewModel<SignInState>(SignInState()) {
    
    fun onFieldChanged(type: InputFieldType, field: String) {
        state = when (type) {
            InputFieldType.EMAIL -> state.copy(email = InputFieldState(field))
            InputFieldType.PASSWORD -> state.copy(password = InputFieldState(field))
            else -> state
        }
    }
    
    fun signInWithEmailAndPassword() {
        scopeLaunch(
            context = dispatchers.io(),
            onLoading = { setLoading() },
            onError = { showError(UiText.Resource(it.messageId)); revokeLoading() },
            onFinally = { revokeLoading() }
        ) {
            signInWithEmailAndPasswordUseCase.signInWithEmailAndPassword(state).let {
                state = it
            }
        }
    }
    
    fun signInWithGoogle(
        signWithGoogleLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        signInClient: SignInClient
    ) {
        scopeLaunch(
            context = dispatchers.io(),
            onLoading = { setLoading() },
            onError = { showError(UiText.Resource(it.messageId)); revokeLoading() }
        ) {
            signInWithGoogleUseCase.signInWithGoogle(signWithGoogleLauncher, signInClient)
        }
    }
    
    fun signInAnonymously() {
        scopeLaunch(
            context = dispatchers.io(),
            onLoading = { setLoading() },
            onError = { showError(UiText.Resource(it.messageId)); revokeLoading() },
            onFinally = { revokeLoading() }
        ) {
            signInAnonymouslyUseCase.signInAnonymously()
            state = state.copy(signInResult = SignInResult.Success)
        }
    }
    
    fun forgotPassword() {
    
    }
    
    fun changeGoogleIdToCredential(result: ActivityResult, signInClient: SignInClient) {
        scopeLaunch(
            context = dispatchers.io(),
            onLoading = { setLoading() },
            onError = { showError(UiText.Resource(it.messageId)); revokeLoading() },
            onFinally = { revokeLoading() }
        ) {
            signInWithGoogleUseCase.changeGoogleIdToCredential(result, signInClient)
            state = state.copy(signInResult = SignInResult.Success)
        }
    }
    
    fun clearEmail() {
        state = state.copy(email = InputFieldState(""))
    }
    
    private fun setLoading() {
        state = state.copy(isLoading = true)
    }
    
    private fun revokeLoading() {
        state = state.copy(isLoading = false)
    }
    
    private fun showError(message: UiText) {
        state = state.copy(errorEvent = ErrorEvent(message).also { it.trigger() })
    }
    
    fun onConsumeError() {
        state = state.copy(errorEvent = ErrorEvent.CONSUMED)
    }
}
