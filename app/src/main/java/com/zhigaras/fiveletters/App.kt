package com.zhigaras.fiveletters

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.room.Room
import com.zhigaras.fiveletters.data.Repository
import com.zhigaras.fiveletters.data.WordDatabase
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.domain.StringConverter
import com.zhigaras.fiveletters.domain.WordCheckable
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.ViewModelFactory

class App : Application(), ProvideViewModel {
    
    private lateinit var wordDatabase: WordDatabase
    private lateinit var viewModelFactory: ViewModelFactory
    
    override fun onCreate() {
        super.onCreate()
        
        wordDatabase = Room
            .databaseBuilder(
                this,
                WordDatabase::class.java,
                WordDatabase.DATABASE_NAME
            ).createFromAsset("database/word.db").build()
        
        viewModelFactory = ViewModelFactory(
            GameStateController.Base(
                StringConverter.Base(),
                WordCheckable.Base()
            ),
            Repository.Base(wordDatabase.getWordDao())
        )
    }
    
    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner) =
        ViewModelProvider(owner, viewModelFactory)[clazz]
}

interface ProvideViewModel {
    
    fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T
}