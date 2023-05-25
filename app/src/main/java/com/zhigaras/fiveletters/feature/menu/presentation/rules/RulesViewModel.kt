package com.zhigaras.fiveletters.feature.menu.presentation.rules

import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetRulesUseCase
import com.zhigaras.fiveletters.feature.play.domain.model.RowState

class RulesViewModel(
    getRulesUseCase: GetRulesUseCase
) : BaseViewModel<List<RowState>>(getRulesUseCase.getRulesRows())