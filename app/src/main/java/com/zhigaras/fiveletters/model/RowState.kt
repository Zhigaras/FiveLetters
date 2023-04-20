package com.zhigaras.fiveletters.model

abstract class RowState {
    
    abstract val row: List<LetterState>
    abstract val isRowFull: Boolean
    abstract val isRowOpened: Boolean
    val label: String = this::class.java.simpleName
    
    class Empty(override val row: List<LetterState>) : RowState() {
        override val isRowFull: Boolean = false
        override val isRowOpened: Boolean = false
    }
    class UncheckedWord(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = true
        override val isRowOpened: Boolean = false
    }
    
    class InvalidWord(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = true
        override val isRowOpened: Boolean = false
    }
    
    class NotFullRow(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = false
        override val isRowOpened: Boolean = false
    }
    
    class Wrong(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = true
        override val isRowOpened: Boolean = true
    }
    
    class Right(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = true
        override val isRowOpened: Boolean = true
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