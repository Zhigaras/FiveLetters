package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.presentation.LetterItem
import com.zhigaras.fiveletters.presentation.LetterType

interface WordCheckable {
    
    fun checkWord(word: List<Char>, origin: String): List<LetterItem>
    
    class Base() : WordCheckable {
        
        override fun checkWord(word: List<Char>, origin: String): List<LetterItem> {
            val originCharList = origin.uppercase().toList()
            val uppercaseChars = word.map { it.uppercaseChar() }
            val result = emptyList<LetterItem>().toMutableList()
            originCharList.zip(uppercaseChars) { o, w ->
                if (w == o) {
                    result.add(LetterItem.Exact(LetterType.Card, w))
                } else if (originCharList.contains(w)) {
                    result.add(LetterItem.Right(LetterType.Card, w))
                } else {
                    result.add(LetterItem.Wrong(LetterType.Card, w))
                }
            }
            return result.toList()
        }
    }
}