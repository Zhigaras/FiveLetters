package com.zhigaras.fiveletters.feature.menu.data

import com.zhigaras.fiveletters.feature.menu.domain.RulesRepository
import com.zhigaras.fiveletters.feature.play.domain.model.LetterState
import com.zhigaras.fiveletters.feature.play.domain.model.RowState
import com.zhigaras.fiveletters.feature.play.domain.usecases.gamelogic.RowStateController

class RulesRepositoryImpl(
    private val rowStateController: RowStateController.Confirm
) : RulesRepository {
    
    private val rulesOrigin = "песня"
    
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