package com.zhigaras.fiveletters.feature.auth.data

import android.util.Patterns
import com.zhigaras.fiveletters.feature.auth.domain.CredentialsValidator
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldValidity
import com.zhigaras.fiveletters.feature.auth.domain.model.ResetPasswordState

class ResetPasswordEmailValidatorImpl : CredentialsValidator.ResetPasswordEmailValidator {
    
    override fun validate(state: ResetPasswordState): ResetPasswordState {
        return if (!Patterns.EMAIL_ADDRESS.matcher(state.email.value).matches())
            state.copy(
                email = state.email
                    .copy(validity = InputFieldValidity.Invalid.InvalidEmail()),
                isValid = false
            )
        else state.copy(isValid = true)
    }
}