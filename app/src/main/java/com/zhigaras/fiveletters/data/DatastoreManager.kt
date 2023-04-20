package com.zhigaras.fiveletters.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

interface DatastoreManager : UsernameInteract, UserStatInteract.Read, UserStatInteract.Write {
    
    class Base(
        private val datastore: DataStore<Preferences>
    ) : DatastoreManager {
        
        private val usernameKey = stringPreferencesKey("username")
        private val gamesCountKey = intPreferencesKey("gamesCount")
        
        override suspend fun saveUsername(name: String) {
            datastore.edit { prefs ->
                prefs[usernameKey] = name
            }
        }
        
        override suspend fun readUsername(): String {
            return datastore.edit {}[usernameKey] ?: ""
        }
        
        override suspend fun incrementGamesCounter() {
            datastore.edit { prefs ->
                val games = prefs[gamesCountKey]
                if (games == null) prefs[gamesCountKey] = 1
                else prefs[gamesCountKey] = games + 1
            }
        }
        
        override suspend fun getGamesCount(): Flow<Int> = datastore.data.transform {
            val counter = it[gamesCountKey]
            if (counter == null) emit(0)
            else emit(counter)
        }
        
        companion object {
            const val PREFERENCES_STORE_NAME = "user_datastore"
        }
    }
}