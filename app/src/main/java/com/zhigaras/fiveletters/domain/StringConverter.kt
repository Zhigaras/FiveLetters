package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.presentation.LetterItem

interface StringConverter {
    
    fun convertLetterToCharList(letters: List<LetterItem>): List<Char>
    
    class Base() : StringConverter {
        
        override fun convertLetterToCharList(letters: List<LetterItem>): List<Char> {
            return letters.map { it.char }
        }
    }
}