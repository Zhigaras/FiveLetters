package com.zhigaras.fiveletters.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zhigaras.fiveletters.data.*

interface Core : ProvideRepository {
    
    fun provideDispatchers(): DispatchersModule
    
    fun provideDatabase(): WordDatabase
    
    fun provideDatastore(): DataStore<Preferences>
    
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
        
        override fun provideDispatchers(): DispatchersModule = dispatchersModule
        
        override fun provideDatabase(): WordDatabase = databaseModule.provideDatabase()
        
        override fun provideDatastore(): DataStore<Preferences> = datastoreModule.provideDatastore()
        
        override fun provideMainRepository(): MainRepository =
            MainRepository.Base(databaseModule.provideDatabase().getWordDao())
        
        override fun provideUsernameRepository(): UsernameRepository =
            UsernameRepository.Base(DatastoreManager.Base(provideDatastore()))
        
        override fun provideUserStatRepository(): UserStatRepository =
            UserStatRepository.Base(provideDatabase().getWordDao())
        
    }
}

interface ProvideRepository {
    
    fun provideMainRepository(): MainRepository
    
    fun provideUsernameRepository(): UsernameRepository
    
    fun provideUserStatRepository(): UserStatRepository
}