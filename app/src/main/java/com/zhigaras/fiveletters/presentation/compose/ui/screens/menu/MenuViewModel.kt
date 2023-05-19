package com.zhigaras.fiveletters.presentation.compose.ui.screens.menu

import android.util.Log
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.UserStatRepository
import com.zhigaras.fiveletters.domain.menu.GetUserInfoUseCase
import com.zhigaras.fiveletters.domain.menu.RulesInteractor
import com.zhigaras.fiveletters.model.menu.UserStat
import com.zhigaras.fiveletters.model.play.RowState
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MenuViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val userStatRepository: UserStatRepository,
    private val dispatchers: DispatchersModule,
    rulesInteractor: RulesInteractor
) : BaseViewModel<List<RowState>>(rulesInteractor.getRulesRows()), MenuInteract {
    
    fun getUser() {
        getUserInfoUseCase.getUser().let {
            Log.d("AAA", it.email) //TODO remove after debugging
        }
    }
    
    override fun userStatFlow() = flow {
        val stat = userStatRepository.getUserStatFlow()
        emitAll(stat)
    }.flowOn(dispatchers.io())
    
}

interface MenuInteract {
    
    fun userStatFlow(): Flow<UserStat>
    
}