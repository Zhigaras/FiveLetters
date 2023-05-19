package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhigaras.fiveletters.core.Core
import com.zhigaras.fiveletters.data.AuthRepository
import com.zhigaras.fiveletters.data.CredentialsValidator
import com.zhigaras.fiveletters.domain.auth.SignUpWithEmailAndPasswordUseCase
import com.zhigaras.fiveletters.domain.auth.SignWithGoogleUseCase
import com.zhigaras.fiveletters.model.play.Alphabet
import com.zhigaras.fiveletters.domain.play.GameStateController
import com.zhigaras.fiveletters.domain.play.KeyboardStateController
import com.zhigaras.fiveletters.domain.play.RowStateController
import com.zhigaras.fiveletters.domain.menu.RulesInteractor
import com.zhigaras.fiveletters.domain.play.WordCheckable
import com.zhigaras.fiveletters.presentation.compose.ui.screens.signin.SignInViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.MenuViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.PlayViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.signup.SignUpViewModel

class ViewModelFactory(
    private val core: Core
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dispatchers = core.provideDispatchers()
        val mainRepository = core.provideMainRepository()
        val stateSaver = core.provideStateSaver()
        val authRepository = AuthRepository.Base(core.provideFirebaseAuth())
        val rowStateController = RowStateController.Base(WordCheckable.Base())
        val viewModel = when (modelClass) {
            PlayViewModel::class.java ->
                PlayViewModel(
                    GameStateController.Base(
                        rowStateController,
                        KeyboardStateController.Base(Alphabet.Ru()),
                        mainRepository
                    ),
                    mainRepository,
                    dispatchers,
                    stateSaver
                )
            
            MenuViewModel::class.java -> MenuViewModel(
                core.provideUserStatRepository(),
                dispatchers,
                RulesInteractor.Base(rowStateController)
            )
            
            SignInViewModel::class.java -> SignInViewModel(
                AuthRepository.Base(core.provideFirebaseAuth()),
                CredentialsValidator.Base(),
                dispatchers
            )
    
            SignUpViewModel::class.java -> SignUpViewModel(
                SignWithGoogleUseCase.Base(authRepository),
                SignUpWithEmailAndPasswordUseCase.Base(CredentialsValidator.Base(), authRepository),
                dispatchers
            )
            
            else -> throw IllegalArgumentException("Unknown class name: $modelClass")
        }
        return viewModel as T
    }
}