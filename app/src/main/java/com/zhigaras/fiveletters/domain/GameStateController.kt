package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.Constants
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.model.RowState
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType

interface GameStateController {
    
    fun getStartGameState(): GameState
    
    fun inputLetter(char: Char): GameState
    
    fun removeLetter(): GameState
    
    fun checkGameState(origin: String): GameState
    
    fun checkRowState(row: List<LetterState>): RowState
    
    class Base(
        private val stringConverter: StringConverter,
        private val wordCheckable: WordCheckable
    ) : GameStateController {
        
        private var gameState: GameState = GameState.Start()
        private var cursorRow = 0
        private var cursorColumn = 0
        
        override fun getStartGameState() = GameState.Start()
        
        override fun inputLetter(char: Char): GameState {
            if (cursorColumn != Constants.MAX_COLUMN) {
                val snapshot = gameState.result.toMutableList()
                val currentRow = snapshot[cursorRow].row.toMutableList()
                currentRow[cursorColumn] = LetterState.UserClicked(LetterType.Card, char)
                snapshot[cursorRow] = RowState.Progress(currentRow.toList())
                cursorColumn++
                gameState = GameState.Progress(snapshot.toList())
            }
            return gameState
        }
        
        override fun removeLetter(): GameState {
            if (cursorColumn != 0) {
                cursorColumn--
                val snapshot = gameState.result.toMutableList()
                val currentRow = snapshot[cursorRow].row.toMutableList()
                currentRow[cursorColumn] = LetterState.Default(LetterType.Card, ' ')
                snapshot[cursorRow] = RowState.Progress(currentRow.toList())
                gameState = GameState.Progress(snapshot)
            }
            return gameState
        }
        
        override fun checkGameState(origin: String): GameState {
            val snapshot = gameState.result.toMutableList()
            val wordToCheck = stringConverter.convertLetterToCharList(snapshot[cursorRow].row)
            val checkedWord = wordCheckable.checkWord(wordToCheck, origin)
            snapshot[cursorRow] = checkRowState(checkedWord)
            gameState = GameState.Progress(snapshot)
            if (snapshot.any {it is RowState.Right})
                gameState = GameState.Win(snapshot)
            if (cursorRow == Constants.MAX_ROWS - 1)
                gameState = GameState.GameOver(snapshot)
            cursorRow++
            cursorColumn = 0
            return gameState
        }
        
        override fun checkRowState(row: List<LetterState>): RowState {
            if (row.all { it is LetterState.Exact }) {
                return RowState.Right(row)
            }
            return RowState.Wrong(row)
        }
    }
}