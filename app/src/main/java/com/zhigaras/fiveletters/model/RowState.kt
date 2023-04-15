package com.zhigaras.fiveletters.model

abstract class RowState {
    
    abstract val row: List<LetterState>
    
    class Empty(override val row: List<LetterState>) : RowState()
    
    abstract class Append() : RowState() {
        
        abstract class FullRow() : Append() {
            
            class UncheckedWord(override val row: List<LetterState>) : FullRow()
            
            class InvalidWord(override val row: List<LetterState>) : FullRow()
            
        }
        
        class NotFullRow(override val row: List<LetterState>) : Append()
    }
    
    class Remove(override val row: List<LetterState>) : RowState()
    
    abstract class Opened() : RowState() {
        
        class Wrong(override val row: List<LetterState>) : Opened()
        
        class Right(override val row: List<LetterState>) : Opened()
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RowState) return false
        if (this::class.java != other::class.java) return false
        return row == other.row
    }
    
    override fun hashCode(): Int {
        return row.hashCode()
    }
    
}