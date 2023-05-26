package com.zhigaras.fiveletters.feature.play.domain.usecases

import com.zhigaras.fiveletters.feature.play.domain.WordsRepository

interface WordsUseCase {
    
    suspend fun getNewWord(): String
    
    suspend fun isWordValid(word: String): Boolean
    
    class Base(private val wordsRepository: WordsRepository) : WordsUseCase {
        
        override suspend fun getNewWord(): String {
            return wordsRepository.getNewWord()
        }
        
        override suspend fun isWordValid(word: String): Boolean {
            return wordsRepository.isWordValid(word)
        }
    }
}