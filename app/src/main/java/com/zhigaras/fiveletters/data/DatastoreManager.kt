package com.zhigaras.fiveletters.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

interface DatastoreManager {
    
    suspend fun saveUsername(name: String)
    
    suspend fun readUsername(): String
    
    class Base(
        private val datastore: DataStore<Preferences>
    ) : DatastoreManager {
        
        private val usernameKey = stringPreferencesKey("username")
        
        override suspend fun saveUsername(name: String) {
            datastore.edit { prefs ->
                prefs[usernameKey] = name
            }
        }
        
        override suspend fun readUsername(): String {
            return datastore.edit {}[usernameKey] ?: ""
        }
        
        companion object {
            const val PREFERENCES_STORE_NAME = "user_datastore"
        }
    }
}