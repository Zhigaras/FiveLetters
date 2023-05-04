package com.zhigaras.fiveletters.data

import com.zhigaras.fiveletters.model.GameState

interface StateSaver {
    
    interface SaveState : StateSaver {
        
        suspend fun saveState(state: GameState)
        
    }
    
    interface RestoreState : StateSaver {
        
        suspend fun restoreState(): GameState?
        
    }
    
    interface Mutable : SaveState, RestoreState
    
    class Base(
        private val datastoreManager: DatastoreManager,
        private val serializer: MoshiSerializer,
    ) : Mutable {
        override suspend fun saveState(state: GameState) {
            datastoreManager.saveState(serializer.serialize(state))
        }
        
        override suspend fun restoreState(): GameState? {
            val json = datastoreManager.restoreState() ?: return null
            return try {
                serializer.deserialize(json)
            } catch (_: Throwable) {
                null
            }
        }
    }
}