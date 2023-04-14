package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhigaras.fiveletters.core.Core
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.Alphabet
import com.zhigaras.fiveletters.data.DatastoreManager
import com.zhigaras.fiveletters.data.UsernameRepository
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.domain.KeyboardStateController
import com.zhigaras.fiveletters.domain.WordCheckable

class ViewModelFactory(
    private val core: Core
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val usernameRepository =
            UsernameRepository.Base(DatastoreManager.Base(core.provideDatastore()))
        val dispatchers = core.provideDispatchers()
        val repository = core.provideRepository()
        val viewModel = when (modelClass) {
            PlayViewModel::class.java ->
                PlayViewModel(
                    GameStateController.Base(
                        WordCheckable.Base(repository)
                    ), KeyboardStateController.Base(Alphabet.Base.Ru()),
                    repository,
                    DispatchersModule.Base()
                )
            WelcomeViewModel::class.java -> WelcomeViewModel(usernameRepository, dispatchers)
            AuthViewModel::class.java -> AuthViewModel(usernameRepository, dispatchers)
            else -> throw IllegalArgumentException("Unknown class name: $modelClass")
        }
        return viewModel as T
    }
}