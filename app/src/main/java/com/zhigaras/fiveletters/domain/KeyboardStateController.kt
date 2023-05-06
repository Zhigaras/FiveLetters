package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.Alphabet
import com.zhigaras.fiveletters.model.Keyboard
import com.zhigaras.fiveletters.model.LetterState

interface KeyboardStateController {
    
    fun updateKeyboard(keyboard: Keyboard, openedLetters: List<LetterState>): Keyboard
    
    fun getDefaultKeyboard(): Keyboard
    
    fun needToUpdateKey(currentLetter: LetterState, incomingLetter: LetterState): Boolean
    
    class Base(private val alphabetInstance: Alphabet) : KeyboardStateController {
        
        override fun updateKeyboard(
            keyboard: Keyboard,
            openedLetters: List<LetterState>
        ): Keyboard {
            val keys = keyboard.keys.map { row ->
                row.map { letter ->
                    updateKey(openedLetters, letter)
                }
            }
            return Keyboard.Progress(keys)
        }
        
        fun updateKey(openedLetters: List<LetterState>, letter: LetterState): LetterState {
            var updateLetter = letter
            openedLetters.forEach { openedLetter ->
                if (needToUpdateKey(letter, openedLetter))
                    updateLetter = openedLetter.convertCardToKey()
            }
            return updateLetter
        }
        
        override fun needToUpdateKey(
            currentLetter: LetterState,
            incomingLetter: LetterState
        ): Boolean {
            return currentLetter.char == incomingLetter.char && incomingLetter.isBetter(
                currentLetter
            )
        }
        
        override fun getDefaultKeyboard(): Keyboard {
            return Keyboard.Default(alphabetInstance)
        }
    }
}