package com.zhigaras.fiveletters.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

interface DatastoreManager : UserStatInteract.Mutable, UserStatInteract.RulesIndicationControl {
    
    suspend fun saveState(json: String)
    
    suspend fun restoreState(): String?
    
    class Base(
        private val datastore: DataStore<Preferences>
    ) : DatastoreManager {
        
        private val gamesCountKey = intPreferencesKey("gamesCount")
        private val gameStateKey = stringPreferencesKey("gameState")
        private val rulesWasShownKey = booleanPreferencesKey("rulesWasShown")
        
        override suspend fun saveState(json: String) {
            datastore.edit { prefs ->
                prefs[gameStateKey] = json
            }
        }
        
        override suspend fun restoreState(): String? {
            return datastore.data.first()[gameStateKey]
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
        
        override suspend fun setRulesWasShown() {
            datastore.edit { prefs ->
                prefs[rulesWasShownKey] = true
            }
        }
        
        override suspend fun wasRulesShown(): Flow<Boolean> {
            return datastore.data.transform {
                emit(it[rulesWasShownKey] == true)
            }
        }
        
        companion object {
            const val PREFERENCES_STORE_NAME = "user_datastore"
        }
    }
}