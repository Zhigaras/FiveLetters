package com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.signup

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.core.presentation.consumed
import com.zhigaras.fiveletters.core.presentation.triggered
import com.zhigaras.fiveletters.domain.auth.SignUpWithEmailAndPasswordUseCase
import com.zhigaras.fiveletters.domain.auth.SignWithGoogleUseCase
import com.zhigaras.fiveletters.model.auth.InputFieldState
import com.zhigaras.fiveletters.model.auth.InputFieldType
import com.zhigaras.fiveletters.model.auth.SignUpResult
import com.zhigaras.fiveletters.model.auth.SignUpState
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.BaseViewModel

class SignUpViewModel(
    private val signInWithGoogleUseCase: SignWithGoogleUseCase,
    private val signUpWithEmailAndPasswordUseCase: SignUpWithEmailAndPasswordUseCase,
    private val dispatchers: DispatchersModule
) : BaseViewModel<SignUpState>(SignUpState()) {
    
    fun onFieldChanged(type: InputFieldType, field: String) {
        state = when (type) {
            InputFieldType.EMAIL -> state.copy(email = InputFieldState(field))
            InputFieldType.PASSWORD -> state.copy(password = InputFieldState(field))
            InputFieldType.REPEAT_PASSWORD -> state.copy(passwordRepeat = InputFieldState(field))
        }
    }
    
    fun clearEmail() {
        state = state.copy(email = InputFieldState(""))
    }
    
    fun signUpWithEmailAndPassword() {
        scopeLaunch(
            context = dispatchers.io()
        ) {
            state =
                signUpWithEmailAndPasswordUseCase.signUpWithEmailAndPassword(state) //TODO refactor
        }
    }
    
    fun signUpWithGoogle(
        signWithGoogleLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        signInClient: SignInClient
    ) {
        scopeLaunch(
            onLoading = { setLoading() },
            onError = { showError(UiText.Resource(it.messageId)); revokeLoading() }
        ) {
            signInWithGoogleUseCase.signInWithGoogle(signWithGoogleLauncher, signInClient)
        }
    }
    
    fun changeGoogleIdToCredential(result: ActivityResult, signInClient: SignInClient) {
        scopeLaunch(
            onLoading = { setLoading() },
            onError = { showError(UiText.Resource(it.messageId)); revokeLoading() },
            onFinally = { revokeLoading() }
        ) {
            signInWithGoogleUseCase.changeGoogleIdToCredential(result, signInClient)
            state = state.copy(signUpResult = SignUpResult.Success)
        }
    }
    
    private fun setLoading() {
        state = state.copy(isLoading = true)
    }
    
    private fun revokeLoading() {
        state = state.copy(isLoading = false)
    }
    
    private fun showError(message: UiText) {
        state = state.copy(errorEvent = triggered(message))
    }
    
    fun onConsumeError() {
        state = state.copy(errorEvent = consumed())
    }
}