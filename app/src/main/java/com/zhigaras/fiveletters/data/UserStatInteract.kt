package com.zhigaras.fiveletters.data

import kotlinx.coroutines.flow.Flow

interface UserStatInteract {
    
    interface Write : UserStatInteract {
        
        suspend fun incrementGamesCounter()
        
    }
    
    interface Read : UserStatInteract {
        
        suspend fun getGamesCount(): Flow<Int>
        
        suspend fun wasRulesShown(): Flow<Boolean>
        
    }
    
    interface RulesIndicationControl {
        
        suspend fun setRulesWasShown()
        
    }
    
    interface Mutable : Read, Write, RulesIndicationControl
}