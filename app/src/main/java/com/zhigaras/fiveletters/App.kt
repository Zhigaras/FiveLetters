package com.zhigaras.fiveletters

import android.app.Application
import androidx.room.Room
import com.zhigaras.fiveletters.data.Repository
import com.zhigaras.fiveletters.data.WordDatabase
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.domain.StringConverter
import com.zhigaras.fiveletters.domain.WordCheckable
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel

class App : Application(), ProvideViewModel {
    
    private lateinit var wordDatabase: WordDatabase
    private lateinit var playViewModel: PlayViewModel
    
    override fun onCreate() {
        super.onCreate()
        
        wordDatabase = Room
            .databaseBuilder(
                this,
                WordDatabase::class.java,
                WordDatabase.DATABASE_NAME
            ).createFromAsset("database/word.db").build()
        
        playViewModel = PlayViewModel(
            GameStateController.Base(
                StringConverter.Base(),
                WordCheckable.Base()
            ),
            Repository.Base(wordDatabase.getWordDao())
        )
    }
    
    override fun provideViewModel() = playViewModel
}

interface ProvideViewModel {
    
    fun provideViewModel(): PlayViewModel
}