package com.zhigaras.fiveletters.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

interface DatastoreManager : UsernameInteract, UserStatInteract.Mutable {
    
    suspend fun saveState(json: String)
    
    suspend fun restoreState(): String
    
    class Base(
        private val datastore: DataStore<Preferences>
    ) : DatastoreManager {
        
        private val usernameKey = stringPreferencesKey("username")
        private val gamesCountKey = intPreferencesKey("gamesCount")
        private val gameStateKey = stringPreferencesKey("gameState")
    
        override suspend fun saveUsername(name: String) {
            datastore.edit { prefs ->
                prefs[usernameKey] = name
            }
        }
    
        override suspend fun saveState(json: String) {
            datastore.edit { prefs ->
                prefs[gameStateKey] = json
            }
        }
    
        override suspend fun restoreState(): String {
            return datastore.data.first()[gameStateKey] ?: ""
        }
    
        override suspend fun readUsername(): String {
            return datastore.data.first()[usernameKey] ?: ""
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