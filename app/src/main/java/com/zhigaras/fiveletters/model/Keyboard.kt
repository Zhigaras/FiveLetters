package com.zhigaras.fiveletters.model

import com.squareup.moshi.JsonClass

abstract class Keyboard {
    
    abstract val keys: List<List<LetterState>>
    
    @JsonClass(generateAdapter = true)
    class Default(val alphabet: Alphabet) : Keyboard() {
        
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
    
    @JsonClass(generateAdapter = true)
    class Progress(override val keys: List<List<LetterState>>) : Keyboard()
}