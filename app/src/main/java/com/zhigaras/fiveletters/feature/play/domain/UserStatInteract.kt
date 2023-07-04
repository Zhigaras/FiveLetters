package com.zhigaras.fiveletters.feature.play.domain

import kotlinx.coroutines.flow.Flow

interface UserStatInteract {
    
    interface Write : UserStatInteract {
        
        suspend fun incrementGamesCounter()
        
    }
    
    interface Read : UserStatInteract {
        
        suspend fun getGamesCount(): Flow<Int>
        
    }
    
    interface Mutable : Read, Write
}