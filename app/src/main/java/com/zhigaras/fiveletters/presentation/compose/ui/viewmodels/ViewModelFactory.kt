package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhigaras.fiveletters.core.Core
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.Alphabet
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.domain.KeyboardStateController
import com.zhigaras.fiveletters.domain.WordCheckable

class ViewModelFactory(
    private val core: Core
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dispatchers = core.provideDispatchers()
        val repository = core.provideMainRepository()
        val userRepository = core.provideUsernameRepository()
        val viewModel = when (modelClass) {
            PlayViewModel::class.java ->
                PlayViewModel(
                    GameStateController.Base(
                        WordCheckable.Base(repository)
                    ), KeyboardStateController.Base(Alphabet.Base.Ru()),
                    repository,
                    DispatchersModule.Base()
                )
            WelcomeViewModel::class.java -> WelcomeViewModel(userRepository, dispatchers)
            AuthViewModel::class.java -> AuthViewModel(userRepository, dispatchers)
            MenuViewModel::class.java -> MenuViewModel(
                core.provideUserStatRepository(),
                dispatchers
            )
            else -> throw IllegalArgumentException("Unknown class name: $modelClass")
        }
        return viewModel as T
    }
}