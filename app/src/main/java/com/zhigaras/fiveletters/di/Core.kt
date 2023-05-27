package com.zhigaras.fiveletters.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zhigaras.fiveletters.datastore.DatastoreManager
import com.zhigaras.fiveletters.datastore.DatastoreModule
import com.zhigaras.fiveletters.feature.menu.data.UserStatRepositoryImpl
import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import com.zhigaras.fiveletters.feature.play.data.GameStateRepositoryImpl
import com.zhigaras.fiveletters.feature.play.data.MoshiSerializer
import com.zhigaras.fiveletters.feature.play.domain.GameStateRepository

interface Core : ProvideRepository {
    
    fun provideDispatchers(): DispatchersModule
    
    fun provideDatastore(): DataStore<Preferences>
    
    fun provideFirebaseAuth(): FirebaseAuth
    
    class Base(
        context: Context,
    ) : Core {
        
        private val datastoreModule = DatastoreModule.Base(context)
        
        private val dispatchersModule by lazy {
            DispatchersModule.Base()
        }
        
        private val datastoreManager = DatastoreManager.Base(provideDatastore())
        
        override fun provideDispatchers(): DispatchersModule = dispatchersModule
        
        override fun provideDatastore(): DataStore<Preferences> = datastoreModule.provideDatastore()
        
        override fun provideUserStatRepository(): UserStatRepository =
//            UserStatRepositoryImpl(provideDatabase().getWordDao(), datastoreManager) //todo refactor core
            UserStatRepositoryImpl(Firebase.database.reference, provideFirebaseAuth())
        
        override fun provideStateSaver(): GameStateRepository =
            GameStateRepositoryImpl(datastoreManager, MoshiSerializer.Base())
        
        override fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
        
    }
}

interface ProvideRepository {
    
    
    fun provideUserStatRepository(): UserStatRepository
    
    fun provideStateSaver(): GameStateRepository
}