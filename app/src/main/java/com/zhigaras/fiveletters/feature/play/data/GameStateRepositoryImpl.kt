package com.zhigaras.fiveletters.feature.play.data

import com.zhigaras.fiveletters.datastore.DatastoreManager
import com.zhigaras.fiveletters.feature.play.domain.GameStateRepository
import com.zhigaras.fiveletters.feature.play.domain.model.GameState

class GameStateRepositoryImpl(
    private val datastoreManager: DatastoreManager,
    private val serializer: MoshiSerializer,
) : GameStateRepository {
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