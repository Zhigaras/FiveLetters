package com.zhigaras.fiveletters.feature.menu.presentation

import com.kpstv.firebase.ValueEventResponse
import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.di.DispatchersModule
import com.zhigaras.fiveletters.feature.menu.data.model.UserStatDto
import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetUserStatUseCase

class MenuViewModel(
    private val getUserStatUseCase: GetUserStatUseCase,
    private val dispatchers: DispatchersModule
) : BaseViewModel<UserStat>(UserStat.Base.initial) {
    
    init {
        scopeLaunch(dispatchers.io()) {
            getUserStatUseCase.getUsrStatFlow().collect { event ->
                when (event) {
                    is ValueEventResponse.Changed ->
                        event.snapshot.getValue(UserStatDto::class.java)?.let {
                            state = it.map()
                        }
                    
                    is ValueEventResponse.Cancelled -> event.error //todo obtain error
                }
            }
        }
    }
}