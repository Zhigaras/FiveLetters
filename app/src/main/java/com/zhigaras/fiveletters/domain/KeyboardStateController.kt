package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.data.Alphabet
import com.zhigaras.fiveletters.model.Keyboard
import com.zhigaras.fiveletters.model.LetterState

interface KeyboardStateController {
    
    fun updateKeyboard(openedLetters: List<LetterState>): Keyboard
    
    fun getDefaultKeyboard(): Keyboard
    
    fun needToUpdateKey(currentLetter: LetterState, incomingLetter: LetterState): Boolean
    
    class Base(private val alphabetInstance: Alphabet) : KeyboardStateController {
        
        private var keyboard: Keyboard = getDefaultKeyboard()
        
        override fun updateKeyboard(openedLetters: List<LetterState>): Keyboard {
            openedLetters.distinct().forEach { openedLetter ->
                keyboard.keys.forEachIndexed { rowIndex, keyRow ->
                    keyRow.forEachIndexed { keyIndex, letter ->
                        keyboard.keys[rowIndex][keyIndex] =
                            if (needToUpdateKey(letter, openedLetter))
                                openedLetter.convertCardToKey() else letter
                    }
                }
            }
            return keyboard
        }
        
        override fun needToUpdateKey(
            currentLetter: LetterState,
            incomingLetter: LetterState
        ): Boolean {
            return currentLetter.char == incomingLetter.char && incomingLetter.isBetter(
                currentLetter
            )
        }
        
        override fun getDefaultKeyboard() = Keyboard.Base(alphabetInstance)
    }
}