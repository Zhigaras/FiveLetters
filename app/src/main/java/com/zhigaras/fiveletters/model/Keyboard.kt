package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.data.Alphabet

abstract class Keyboard {
    
    abstract val keys: List<List<LetterState>>
    
    class Default(private val alphabet: Alphabet) : Keyboard() {
        
        override val keys: List<List<LetterState>> =
            alphabet.getKeyboardAlphabet().map { row ->
                row.map {
                    LetterState.Default(
                        type = LetterType.Key(),
                        char = it.uppercaseChar()
                    )
                }.toMutableList<LetterState>()
            }.toMutableList()
    }
    
    class Progress(override val keys: List<List<LetterState>>) : Keyboard()
}