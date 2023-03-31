package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.presentation.Letter

interface StringConverter {
    
    fun convertLetterToCharList(letters: List<Letter>): List<Char>
    
    class Base() : StringConverter {
        
        override fun convertLetterToCharList(letters: List<Letter>): List<Char> {
            return letters.map { it.char }
        }
    }
}