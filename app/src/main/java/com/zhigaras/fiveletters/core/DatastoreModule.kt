package com.zhigaras.fiveletters.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.zhigaras.fiveletters.data.DatastoreManager

interface DatastoreModule {
    
    fun provideDatastore(): DataStore<Preferences>
    
    class Base(context: Context) : DatastoreModule {
        
        private val datastore = PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DatastoreManager.Base.PREFERENCES_STORE_NAME) }
        )
        
        override fun provideDatastore(): DataStore<Preferences> = datastore
    }
}