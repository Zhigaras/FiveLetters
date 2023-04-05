package com.zhigaras.fiveletters.model

abstract class RowState {
    
    abstract val row: List<LetterState>
    
    class Empty(override val row: List<LetterState>) : RowState()
    
    class Progress(override val row: List<LetterState>) : RowState()
    
    class Wrong(override val row: List<LetterState>) : RowState()
    
    class Right(override val row: List<LetterState>) : RowState()
    
}