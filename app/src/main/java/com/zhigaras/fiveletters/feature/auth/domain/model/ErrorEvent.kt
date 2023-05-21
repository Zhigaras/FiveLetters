package com.zhigaras.fiveletters.feature.auth.domain.model

import com.zhigaras.fiveletters.core.presentation.UiText

class ErrorEvent(val message: UiText) {
    
    var isVisible = false
        private set
    
    fun trigger() {
        isVisible = true
    }
    
    companion object {
        val CONSUMED = ErrorEvent(UiText.Empty)
    }
}