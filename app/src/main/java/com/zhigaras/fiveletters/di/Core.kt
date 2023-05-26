package com.zhigaras.fiveletters.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.zhigaras.fiveletters.core.data.ProvideInstance
import com.zhigaras.fiveletters.database.WordDatabase
import com.zhigaras.fiveletters.datastore.DatastoreManager
import com.zhigaras.fiveletters.datastore.DatastoreModule
import com.zhigaras.fiveletters.feature.menu.data.UserStatRepositoryImpl
import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import com.zhigaras.fiveletters.feature.play.data.GameStateRepositoryImpl
import com.zhigaras.fiveletters.feature.play.data.MainRepositoryImpl
import com.zhigaras.fiveletters.feature.play.data.MoshiSerializer
import com.zhigaras.fiveletters.feature.play.domain.GameStateRepository
import com.zhigaras.fiveletters.feature.play.domain.MainRepository

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
            MainRepositoryImpl(databaseModule.provideDatabase().getWordDao(), datastoreManager)
        
        override fun provideUserStatRepository(): UserStatRepository =
//            UserStatRepositoryImpl(provideDatabase().getWordDao(), datastoreManager) //todo refactor core
            UserStatRepositoryImpl(FirebaseDatabase.getInstance().reference, provideFirebaseAuth())
        
        override fun provideStateSaver(): GameStateRepository =
            GameStateRepositoryImpl(datastoreManager, MoshiSerializer.Base())
        
        override fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
        
    }
}

interface ProvideRepository {
    
    fun provideMainRepository(): MainRepository
    
    fun provideUserStatRepository(): UserStatRepository
    
    fun provideStateSaver(): GameStateRepository
}