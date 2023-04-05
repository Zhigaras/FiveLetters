package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType

interface WordCheckable {
    
    fun checkWord(word: List<Char>, origin: String): List<LetterState>
    
    class Base : WordCheckable {
        
        override fun checkWord(word: List<Char>, origin: String): List<LetterState> {
            val originCharList = origin.uppercase().toList()
            val uppercaseChars = word.map { it.uppercaseChar() }
            val result = emptyList<LetterState>().toMutableList()
            originCharList.zip(uppercaseChars) { o, w ->
                if (w == o) {
                    result.add(LetterState.Exact(LetterType.Card, w))
                } else if (originCharList.contains(w)) {
                    result.add(LetterState.Right(LetterType.Card, w))
                } else {
                    result.add(LetterState.Wrong(LetterType.Card, w))
                }
            }
            return result.toList()
        }
    }
}