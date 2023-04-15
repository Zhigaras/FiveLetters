package com.zhigaras.fiveletters.model

abstract class RowState {
    
    abstract val row: List<LetterState>
    
    data class Empty(override val row: List<LetterState>) : RowState()
    
    abstract class Append() : RowState() {
        
        abstract class FullRow() : Append() {
            
            data class UncheckedWord(override val row: List<LetterState>) : FullRow()
            
            data class InvalidWord(override val row: List<LetterState>) : FullRow()
            
        }
        
        data class NotFullRow(override val row: List<LetterState>) : Append()
    }
    
    data class Remove(override val row: List<LetterState>) : RowState()
    
    abstract class Opened() : RowState() {
        
        data class Wrong(override val row: List<LetterState>) : Opened()
        
        data class Right(override val row: List<LetterState>) : Opened()
    }
}