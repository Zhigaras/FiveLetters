package com.zhigaras.fiveletters.model

abstract class RowState {
    
    abstract val row: List<LetterState>
    abstract val isRowFull: Boolean
    abstract val isRowOpened: Boolean
    val label: String = this::class.java.simpleName
    
    data class Empty(override val row: List<LetterState>) : RowState() {
        override val isRowFull: Boolean = false
        override val isRowOpened: Boolean = false
    }
    data class UncheckedWord(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = true
        override val isRowOpened: Boolean = false
    }
    
    data class InvalidWord(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = true
        override val isRowOpened: Boolean = false
    }
    
    data class NotFullRow(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = false
        override val isRowOpened: Boolean = false
    }
    
    data class Wrong(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = true
        override val isRowOpened: Boolean = true
    }
    
    data class Right(override val row: List<LetterState>) : RowState(){
        override val isRowFull: Boolean = true
        override val isRowOpened: Boolean = true
    }
}