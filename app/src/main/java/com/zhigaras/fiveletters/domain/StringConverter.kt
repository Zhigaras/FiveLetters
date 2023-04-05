package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.LetterState

interface StringConverter {
    
    fun convertLetterToCharList(letters: List<LetterState>): List<Char>
    
    class Base : StringConverter {
        
        override fun convertLetterToCharList(letters: List<LetterState>): List<Char> {
            return letters.map { it.char }
        }
    }
}