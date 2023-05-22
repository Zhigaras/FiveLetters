package com.zhigaras.fiveletters.feature.auth.data

import android.util.Patterns
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldValidity
import com.zhigaras.fiveletters.feature.auth.domain.model.SignUpState

class SignUpValidatorImpl : CredentialsValidator.SignUpValidator {
    
    override fun validate(state: SignUpState): SignUpState {
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
        if (outState.password.value != outState.passwordRepeat.value)
            outState = outState.copy(
                passwordRepeat = outState.passwordRepeat
                    .copy(validity = InputFieldValidity.Invalid.PasswordMismatch()),
                isValid = false
            )
        return outState
    }
}