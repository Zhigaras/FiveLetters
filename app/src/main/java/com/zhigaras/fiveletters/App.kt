package com.zhigaras.fiveletters

import android.app.Application
import com.zhigaras.fiveletters.data.Repository
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.domain.StringConverter
import com.zhigaras.fiveletters.domain.WordCheckable
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel

class App : Application(), ProvideViewModel {
    
    private lateinit var playViewModel: PlayViewModel
    
    override fun onCreate() {
        super.onCreate()
        playViewModel = PlayViewModel(
            GameStateController.Base(
                StringConverter.Base(),
                WordCheckable.Base()
            ),
            Repository.Base()
        )
    }
    
    override fun provideViewModel() = playViewModel
    
    
}

interface ProvideViewModel {
    
    fun provideViewModel(): PlayViewModel
}