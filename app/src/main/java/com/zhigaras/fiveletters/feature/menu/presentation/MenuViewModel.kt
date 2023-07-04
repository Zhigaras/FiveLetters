package com.zhigaras.fiveletters.feature.menu.presentation

import android.util.Log
import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.di.DispatchersModule
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetRulesUseCase
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetUserInfoUseCase
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetUserStatUseCase
import com.zhigaras.fiveletters.feature.play.domain.model.RowState
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MenuViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserStatUseCase: GetUserStatUseCase,
    private val dispatchers: DispatchersModule,
    getRulesUseCase: GetRulesUseCase
) : BaseViewModel<List<RowState>>(getRulesUseCase.getRulesRows()) {
    
    fun getUser() {
        getUserInfoUseCase.getUser().let {
            Log.d("AAA", it.email) //TODO remove after debugging
        }
    }
    
    fun userStatFlow() = flow {
        val stat = getUserStatUseCase.getUserStatFlow()
        emitAll(stat)
    }.flowOn(dispatchers.io())
    
}