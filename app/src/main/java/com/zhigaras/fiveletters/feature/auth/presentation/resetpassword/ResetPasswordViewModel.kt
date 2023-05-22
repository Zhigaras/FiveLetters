package com.zhigaras.fiveletters.feature.auth.presentation.resetpassword

import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.di.DispatchersModule
import com.zhigaras.fiveletters.feature.auth.domain.model.ErrorEvent
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldState
import com.zhigaras.fiveletters.feature.auth.domain.model.ResetPasswordState
import com.zhigaras.fiveletters.feature.auth.domain.usecases.ResetPasswordUseCase

class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val dispatchers: DispatchersModule
) : BaseViewModel<ResetPasswordState>(ResetPasswordState()) {
    
    fun resetPassword() {
        scopeLaunch(
            context = dispatchers.io(),
            onLoading = { setLoading() },
            onError = { showError(UiText.Resource(it.messageId)); revokeLoading() },
            onFinally = { revokeLoading() }
        ) {
            resetPasswordUseCase.resetPassword(state).let { state = it }
        }
    }
    
    fun onEmailChanged(field: String) {
        state = state.copy(email = InputFieldState(field), errorEvent = ErrorEvent.CONSUMED)
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