package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.RowState
import com.zhigaras.fiveletters.model.Word

interface RulesInteractor {
    
    fun getRulesRows(): List<RowState>
    
    class Base(
        private val rowStateController: RowStateController.Confirm
    ) : RulesInteractor {
        
        private val rulesOrigin = Word.rulesMock
        
        private val rulesWords = listOf("блеск", "пенал", "песня", "пенся")
        
        private val rulesRowsList: List<RowState> = rulesWords.map { word ->
            RowState.UncheckedWord(word.map { LetterState.UserClicked(it.uppercaseChar()) })
        }
        
        override fun getRulesRows(): List<RowState> {
            return rulesRowsList.mapIndexed { index, rowState ->
                rowStateController.confirmWord(
                    index != rulesRowsList.lastIndex,
                    rulesOrigin,
                    rowState
                )
            }
        }
    }
}