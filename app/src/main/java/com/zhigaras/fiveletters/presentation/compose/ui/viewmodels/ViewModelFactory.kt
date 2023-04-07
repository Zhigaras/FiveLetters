package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhigaras.fiveletters.data.Repository
import com.zhigaras.fiveletters.domain.GameStateController

class ViewModelFactory(
    private val gameStateController: GameStateController,
    private val repository: Repository
): ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == PlayViewModel::class.java) {
            return PlayViewModel(gameStateController, repository) as T
        }
        throw IllegalArgumentException("Unknown class name: $modelClass")
    }
}