package com.zhigaras.fiveletters.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zhigaras.fiveletters.data.*

interface Core : ProvideRepository {
    
    fun provideDispatchers(): DispatchersModule
    
    fun provideDatabase(): WordDatabase
    
    fun provideDatastore(): DataStore<Preferences>
    
    fun provideFirebaseAuth(): FirebaseAuth
    
    class Base(
        context: Context,
        private val provideInstance: ProvideInstance
    ) : Core {
        
        private val datastoreModule = DatastoreModule.Base(context)
        
        private val dispatchersModule by lazy {
            DispatchersModule.Base()
        }
        
        private val databaseModule by lazy {
            provideInstance.provideDatabaseModule()
        }
        
        private val datastoreManager = DatastoreManager.Base(provideDatastore())
        
        override fun provideDispatchers(): DispatchersModule = dispatchersModule
        
        override fun provideDatabase(): WordDatabase = databaseModule.provideDatabase()
        
        override fun provideDatastore(): DataStore<Preferences> = datastoreModule.provideDatastore()
        
        override fun provideMainRepository(): MainRepository =
            MainRepository.Base(databaseModule.provideDatabase().getWordDao(), datastoreManager)
        
        override fun provideUserStatRepository(): UserStatRepository =
            UserStatRepository.Base(provideDatabase().getWordDao(), datastoreManager)
        
        override fun provideStateSaver(): StateSaver.Mutable =
            StateSaver.Base(datastoreManager, MoshiSerializer.Base())
    
        override fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
        
    }
}

interface ProvideRepository {
    
    fun provideMainRepository(): MainRepository
    
    fun provideUserStatRepository(): UserStatRepository
    
    fun provideStateSaver(): StateSaver.Mutable
}