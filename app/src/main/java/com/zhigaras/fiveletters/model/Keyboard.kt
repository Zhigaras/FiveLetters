package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.data.Alphabet

abstract class Keyboard {
    
    abstract val keys: MutableList<MutableList<LetterState>>
    
    class Base(alphabet: Alphabet) : Keyboard(){
        
        override val keys: MutableList<MutableList<LetterState>> = alphabet.getKeyboardAlphabet().map { row ->
            row.map {
                LetterState.Default(
                    type = LetterType.Key,
                    char = it.uppercaseChar()
                )
            }.toMutableList<LetterState>()
        }.toMutableList()
    }
}