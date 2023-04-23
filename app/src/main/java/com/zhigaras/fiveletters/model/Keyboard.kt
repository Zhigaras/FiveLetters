package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.data.Alphabet

data class Keyboard(val keys: KeyboardKeys)

abstract class KeyboardKeys {
    
    abstract val keys: List<List<LetterState>>
    
    class Default(private val alphabet: Alphabet): KeyboardKeys() {
    
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
    
    class Progress(override val keys: List<List<LetterState>>): KeyboardKeys()
}