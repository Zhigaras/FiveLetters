package com.zhigaras.fiveletters.feature.auth.data

import android.util.Patterns
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldValidity
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInState

class SignInValidatorImpl : CredentialsValidator.SignInValidator {
    
    override fun validate(state: SignInState): SignInState {
        var outState = state.copy(isValid = true)
        if (!Patterns.EMAIL_ADDRESS.matcher(outState.email.value).matches())
            outState = outState.copy(
                email = outState.email
                    .copy(validity = InputFieldValidity.Invalid.InvalidEmail()),
                isValid = false
            )
        if (outState.password.value.length < 6)
            outState = outState.copy(
                password = outState.password
                    .copy(validity = InputFieldValidity.Invalid.InvalidPassword()),
                isValid = false
            )
        return outState
    }
}