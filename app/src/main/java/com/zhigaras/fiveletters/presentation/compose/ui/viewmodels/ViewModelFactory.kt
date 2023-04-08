package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhigaras.fiveletters.core.Core
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.domain.StringConverter
import com.zhigaras.fiveletters.domain.WordCheckable

class ViewModelFactory(
    private val core: Core
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            PlayViewModel::class.java ->
                PlayViewModel(
                    GameStateController.Base(
                        StringConverter.Base(),
                        WordCheckable.Base()
                    ), core.provideRepository()
                )
            WelcomeViewModel::class.java ->
                WelcomeViewModel()
            else -> throw IllegalArgumentException("Unknown class name: $modelClass")
        }
        return viewModel as T
    }
}