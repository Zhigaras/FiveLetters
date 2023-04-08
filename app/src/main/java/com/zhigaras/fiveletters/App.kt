package com.zhigaras.fiveletters

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.zhigaras.fiveletters.core.Core
import com.zhigaras.fiveletters.core.ProvideInstance
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.ViewModelFactory

class App : Application(), ProvideViewModel {
    
    private lateinit var core: Core
    private lateinit var viewModelFactory: ViewModelFactory
    
    override fun onCreate() {
        super.onCreate()
        
        val provideInstance =
            if (BuildConfig.DEBUG) ProvideInstance.Mock(this)
            else ProvideInstance.Release(this)
        
        core = Core.Base(this, provideInstance)
        
        viewModelFactory = ViewModelFactory(core)
    }
    
    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner) =
        ViewModelProvider(owner, viewModelFactory)[clazz]
}

interface ProvideViewModel {
    
    fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T
}