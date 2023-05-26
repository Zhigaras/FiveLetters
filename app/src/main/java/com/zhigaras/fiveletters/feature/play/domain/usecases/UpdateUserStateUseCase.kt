package com.zhigaras.fiveletters.feature.play.domain.usecases

import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository

interface UpdateUserStateUseCase {
    
    suspend fun incrementGamesCount()
    
    suspend fun incrementWinsCount()
    
    suspend fun incrementAttempt(lineNumber: Int)
    
    class Base(private val userStatRepository: UserStatRepository) : UpdateUserStateUseCase {
        
        override suspend fun incrementGamesCount() {
            userStatRepository.incrementGamesCount()
        }
        
        override suspend fun incrementWinsCount() {
            userStatRepository.incrementWinsCount()
        }
        
        override suspend fun incrementAttempt(lineNumber: Int) {
            userStatRepository.incrementAttempt(lineNumber)
        }
    }
}