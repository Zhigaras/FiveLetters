package com.zhigaras.fiveletters.model

abstract class RowState {
    
    abstract val row: List<LetterState>
    
    class Empty(override val row: List<LetterState>) : RowState()
    
    abstract class Append() : RowState() {
        
        class FullRow(override val row: List<LetterState>) : Append()
        
        class NotFullRow(override val row: List<LetterState>) : Append()
    }
    
    class Remove(override val row: List<LetterState>) : RowState()
    
    abstract class Opened(): RowState() {
    
        class Wrong(override val row: List<LetterState>) : Opened()
    
        class Right(override val row: List<LetterState>) : Opened()
    }
}