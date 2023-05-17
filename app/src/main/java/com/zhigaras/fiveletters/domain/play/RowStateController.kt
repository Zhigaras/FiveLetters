package com.zhigaras.fiveletters.domain.play

import com.zhigaras.fiveletters.model.play.GameState
import com.zhigaras.fiveletters.model.play.LetterState
import com.zhigaras.fiveletters.model.play.LetterType
import com.zhigaras.fiveletters.model.play.RowState
import com.zhigaras.fiveletters.model.play.Word

interface RowStateController {
    
    interface Mutable : RowStateController {
        
        fun inputLetter(gameState: GameState, char: Char, currentRow: RowState): RowState
        
        fun removeLetter(gameState: GameState, currentRow: RowState, columnCursor: Int): RowState
        
    }
    
    interface Confirm : RowStateController {
        
        fun confirmWord(
            isWordValid: Boolean,
            origin: Word,
            currentRow: RowState
        ): RowState
        
    }
    
    interface Overall : Mutable, Confirm
    
    class Base(private val wordCheckable: WordCheckable) : Overall {
        
        override fun inputLetter(gameState: GameState, char: Char, currentRow: RowState): RowState {
            val row = currentRow.row.toMutableList()
            row[gameState.columnCursor] = LetterState.UserClicked(char)
            return if (gameState.isColumnLast)
                RowState.UncheckedWord(row.toList())
            else
                RowState.NotFullRow(row.toList())
        }
        
        override fun removeLetter(
            gameState: GameState,
            currentRow: RowState,
            columnCursor: Int
        ): RowState {
            val row: MutableList<LetterState> =
                currentRow.row.map { LetterState.UserClicked(it.char) }.toMutableList()
            row[columnCursor] = LetterState.Empty(type = LetterType.Card())
            return RowState.NotFullRow(row.toList())
        }
        
        override fun confirmWord(
            isWordValid: Boolean,
            origin: Word,
            currentRow: RowState
        ): RowState {
            return wordCheckable.checkWord(
                isWordValid,
                currentRow.row.map { it.char },
                origin.word.uppercase()
            )
        }
    }
}