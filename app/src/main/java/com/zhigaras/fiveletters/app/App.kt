package com.zhigaras.fiveletters.app

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zhigaras.fiveletters.BuildConfig
import com.zhigaras.fiveletters.di.Core
import com.zhigaras.fiveletters.di.ViewModelFactory

class App : Application(), ProvideViewModel {
    
    private lateinit var viewModelFactory: ViewModelFactory
    
    override fun onCreate() {
        super.onCreate()
        val isDebug = BuildConfig.DEBUG
        Firebase.analytics.setAnalyticsCollectionEnabled(!isDebug)
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(!isDebug) //todo maybe remove?
        Firebase.database.setPersistenceEnabled(true)
        MobileAds.initialize(this)

//        val provideInstance = ProvideInstance.Release(this)
        
        val core = Core.Base(this)
        
        viewModelFactory = ViewModelFactory(core)
    }
    
    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner) =
        ViewModelProvider(owner, viewModelFactory)[clazz]
}

interface ProvideViewModel {
    
    fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T
}