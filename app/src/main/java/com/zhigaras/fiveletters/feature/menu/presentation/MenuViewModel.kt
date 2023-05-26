package com.zhigaras.fiveletters.feature.menu.presentation

import android.util.Log
import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.di.DispatchersModule
import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetCurrentUserUseCase
import com.zhigaras.fiveletters.feature.menu.domain.usecases.GetUserStatUseCase

class MenuViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserStatUseCase: GetUserStatUseCase,
    private val dispatchers: DispatchersModule
) : BaseViewModel<UserStat>(UserStat.initial) {
    
    //todo либо завести переменную currentUser, либо запрос юзкра перенести в юзкейс
    
    init {
        getUserStat()
    }
    
    fun getUserStat() {
        scopeLaunch(context = dispatchers.io()) {
            state = getUserStatUseCase.getUserStat()
            getUserStatUseCase.tempPutUserStat() //todo remove
            Log.d("AAA get", state.toString()) //todo remove
        }
    }
}