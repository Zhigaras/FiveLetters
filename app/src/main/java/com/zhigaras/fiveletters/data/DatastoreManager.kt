package com.zhigaras.fiveletters.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface DatastoreManager {
    
    fun saveUsername(name: String)
    
    fun readUsername(): String
    
    class Base(
        private val datastore: DataStore<Preferences>
    ) : DatastoreManager {
        
        override fun saveUsername(name: String) {
            TODO("Not yet implemented")
        }
        
        override fun readUsername(): String {
            TODO("Not yet implemented")
        }
        
        
        companion object {
            const val PREFERENCES_STORE_NAME = "user_datastore"
        }
    }
}