package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.Username

interface UsernameRepository {
    
    suspend fun saveUsername(name: String)
    
    suspend fun readUsername(): Username
    
    class Base(
        private val datastoreManager: DatastoreManager.Username
    ) : UsernameRepository {
        
        private val unspecifiedName = "unspecified"
        
        override suspend fun saveUsername(name: String) {
            val nameToSave = name.ifBlank { unspecifiedName }
            datastoreManager.saveUsername(nameToSave)
        }
        
        override suspend fun readUsername(): Username {
            val name = datastoreManager.readUsername()
            if (name.isBlank()) return Username.Loaded.NeedNameRequest()
            if (name == unspecifiedName) return Username.Loaded.Unspecified()
            return Username.Loaded.Specified(name)
        }
    }
}