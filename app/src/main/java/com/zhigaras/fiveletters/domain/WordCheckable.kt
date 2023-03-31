package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.presentation.Letter
import com.zhigaras.fiveletters.presentation.LetterType

interface WordCheckable {
    
    fun checkWord(word: List<Char>): List<Letter>
    
    class Base(origin: String) : WordCheckable {
        
        private val origin = origin.uppercase().toList()
        
        override fun checkWord(word: List<Char>): List<Letter> {
            val uppercaseChars = word.map { it.uppercaseChar() }
            val result = emptyList<Letter>().toMutableList()
            origin.zip(uppercaseChars) { o, w ->
                if (w == o) {
                    result.add(Letter.Exact(LetterType.Card, w))
                } else if (origin.contains(w)) {
                    result.add(Letter.Right(LetterType.Card, w))
                } else {
                    result.add(Letter.Wrong(LetterType.Card, w))
                }
            }
            return result.toList()
        }
    }
}