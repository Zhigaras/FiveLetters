package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhigaras.fiveletters.data.Repository
import com.zhigaras.fiveletters.domain.GameStateController

class ViewModelFactory(
    private val gameStateController: GameStateController,
    private val repository: Repository
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            PlayViewModel::class.java ->
                PlayViewModel(gameStateController, repository)
            WelcomeViewModel::class.java ->
                WelcomeViewModel()
            else -> throw IllegalArgumentException("Unknown class name: $modelClass")
        }
        return viewModel as T
    }
}