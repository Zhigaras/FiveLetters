package com.zhigaras.fiveletters.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhigaras.fiveletters.feature.auth.data.AuthRepositoryImpl
import com.zhigaras.fiveletters.feature.auth.data.SignInValidatorImpl
import com.zhigaras.fiveletters.feature.auth.data.SignUpValidatorImpl
import com.zhigaras.fiveletters.feature.auth.domain.usecases.ForgotPasswordUseCase
import com.zhigaras.fiveletters.feature.auth.domain.usecases.SignInAnonymouslyUseCase
import com.zhigaras.fiveletters.feature.auth.domain.usecases.SignInWithEmailAndPasswordUseCase
import com.zhigaras.fiveletters.feature.auth.domain.usecases.SignUpWithEmailAndPasswordUseCase
import com.zhigaras.fiveletters.feature.auth.domain.usecases.SignInWithGoogleUseCase
import com.zhigaras.fiveletters.feature.auth.presentation.signin.SignInViewModel
import com.zhigaras.fiveletters.feature.auth.presentation.signup.SignUpViewModel
import com.zhigaras.fiveletters.feature.menu.data.RulesRepositoryImpl
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetRulesUseCase
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetUserInfoUseCase
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetUserStatUseCase
import com.zhigaras.fiveletters.feature.menu.presentation.MenuViewModel
import com.zhigaras.fiveletters.feature.play.domain.model.Alphabet
import com.zhigaras.fiveletters.feature.play.domain.usecases.GameStateController
import com.zhigaras.fiveletters.feature.play.domain.usecases.KeyboardStateController
import com.zhigaras.fiveletters.feature.play.domain.usecases.RowStateController
import com.zhigaras.fiveletters.feature.play.domain.usecases.SaveStateUseCase
import com.zhigaras.fiveletters.feature.play.domain.usecases.WordCheckable
import com.zhigaras.fiveletters.feature.play.presentation.PlayViewModel

class ViewModelFactory(
    private val core: Core
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dispatchers = core.provideDispatchers()
        val mainRepository = core.provideMainRepository()
        val stateSaver = core.provideStateSaver()
        val authRepository = AuthRepositoryImpl(core.provideFirebaseAuth())
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
                    SaveStateUseCase.Base(stateSaver)
                )
            
            MenuViewModel::class.java -> MenuViewModel(
                GetUserInfoUseCase.Base(authRepository),
                GetUserStatUseCase.Base(core.provideUserStatRepository()),
                dispatchers,
                GetRulesUseCase.Base(RulesRepositoryImpl(rowStateController))
            )
            
            SignInViewModel::class.java -> SignInViewModel(
                SignInWithEmailAndPasswordUseCase.Base(authRepository, SignInValidatorImpl()),
                SignInWithGoogleUseCase.Base(authRepository),
                SignInAnonymouslyUseCase.Base(authRepository),
                ForgotPasswordUseCase.Base(authRepository),
                dispatchers
            )
            
            SignUpViewModel::class.java -> SignUpViewModel(
                SignUpWithEmailAndPasswordUseCase.Base(authRepository, SignUpValidatorImpl()),
                dispatchers
            )
            
            else -> throw IllegalArgumentException("Unknown class name: $modelClass")
        }
        return viewModel as T
    }
}