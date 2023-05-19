package com.zhigaras.fiveletters.feature.auth.data

import android.util.Patterns
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldValidity
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInState
import com.zhigaras.fiveletters.feature.auth.domain.model.SignUpState

class CredentialsValidatorImpl : CredentialsValidator {
    
    override fun validateSignIn(state: SignInState): SignInState {
        var outState = state
        if (!Patterns.EMAIL_ADDRESS.matcher(outState.email.value).matches())
            outState = outState.copy(
                email = outState.email
                    .copy(validity = InputFieldValidity.Invalid.InvalidEmail())
            )
        if (outState.password.value.length < 6)
            outState = outState.copy(
                password = outState.password
                    .copy(validity = InputFieldValidity.Invalid.InvalidPassword())
            )
        return outState
    }
    
    override fun validateSignUp(state: SignUpState): SignUpState {
        var outState = state
        if (!Patterns.EMAIL_ADDRESS.matcher(outState.email.value).matches())
            outState = outState.copy(
                email = outState.email
                    .copy(validity = InputFieldValidity.Invalid.InvalidEmail())
            )
        if (outState.password.value.length < 6)
            outState = outState.copy(
                password = outState.password
                    .copy(validity = InputFieldValidity.Invalid.InvalidPassword())
            )
        if (outState.password.value != outState.passwordRepeat.value)
            outState = outState.copy(
                passwordRepeat = outState.passwordRepeat
                    .copy(validity = InputFieldValidity.Invalid.PasswordMismatch())
            )
        return outState
    }
}