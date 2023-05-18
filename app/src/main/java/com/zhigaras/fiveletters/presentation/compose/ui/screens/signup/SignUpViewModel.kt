package com.zhigaras.fiveletters.presentation.compose.ui.screens.signup

import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.tasks.Task
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.AuthRepository
import com.zhigaras.fiveletters.data.CredentialsValidator
import com.zhigaras.fiveletters.domain.auth.InputFieldType
import com.zhigaras.fiveletters.model.auth.InputFieldState
import com.zhigaras.fiveletters.model.auth.SignUpState
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.BaseViewModel

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val credentialsValidator: CredentialsValidator,
    private val dispatchers: DispatchersModule
) : BaseViewModel<SignUpState>(SignUpState.empty) {
    
    fun onFieldChanged(type: InputFieldType, field: String) {
        state = when (type) {
            InputFieldType.EMAIL -> state.copy(email = InputFieldState(field))
            InputFieldType.PASSWORD -> state.copy(password = InputFieldState(field))
            InputFieldType.REPEAT_PASSWORD -> state.copy(passwordRepeat = InputFieldState(field))
        }
    }
    
    fun signUpWithEmailAndPassword() {
        scopeLaunch(
            context = dispatchers.io()
        ) {
            state = credentialsValidator.validateSignUp(state)
//            authRepository.createUserWithEmailAndPassword(state.email.value, state.password.value)
        }
    }
    
    fun signInWithGoogle(
        beginSignIn: (BeginSignInRequest) -> Task<BeginSignInResult>,
        launchIntent: (IntentSenderRequest) -> Unit
    ) {
        scopeLaunch {
            beginSignIn(authRepository.getSignInWithGoogleRequest()).addOnSuccessListener {
                try {
                    launchIntent(IntentSenderRequest.Builder(it.pendingIntent).build())
                    Log.d("AAA", "launched")
                } catch (e: Exception) {
                    Log.d("AAA", e.message.toString())
                }
            }.addOnFailureListener {
                Log.d("AAA", it.message.toString())
            }
        }
    }
    
    fun changeGoogleIdToCredential(token: String?) {
        scopeLaunch {
            if (token != null) {
                authRepository.changeGoogleIdToCredential(token)
            }
        }
    }
}