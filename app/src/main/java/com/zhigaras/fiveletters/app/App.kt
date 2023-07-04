package com.zhigaras.fiveletters.app

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.ads.MobileAds
import com.zhigaras.fiveletters.core.data.ProvideInstance
import com.zhigaras.fiveletters.di.Core
import com.zhigaras.fiveletters.di.ViewModelFactory

class App : Application(), ProvideViewModel {
    
    private lateinit var viewModelFactory: ViewModelFactory
    
    override fun onCreate() {
        super.onCreate()
        
        MobileAds.initialize(this)
    
        val provideInstance = ProvideInstance.Release(this)
        
        val core = Core.Base(this, provideInstance)
        
        viewModelFactory = ViewModelFactory(core)
    }
    
    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner) =
        ViewModelProvider(owner, viewModelFactory)[clazz]
}

interface ProvideViewModel {
    
    fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T
}