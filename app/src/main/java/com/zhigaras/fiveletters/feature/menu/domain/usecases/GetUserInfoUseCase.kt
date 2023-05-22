package com.zhigaras.fiveletters.feature.menu.domain.usecases

import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.feature.auth.domain.AuthRepository
import com.zhigaras.fiveletters.feature.menu.domain.model.MenuUser

interface GetUserInfoUseCase {
    
    fun getUser(): MenuUser
    
    class Base(private val authRepository: AuthRepository) : GetUserInfoUseCase {
        
        override fun getUser(): MenuUser { //TODO remove after debugging
            val currentUser = authRepository.getCurrentUser()
            return MenuUser(
                name = currentUser?.displayName?.let { UiText.Dynamic(it) }
                    ?: UiText.Resource(R.string.unnamed),
                email = currentUser?.email ?: "a;kgjadfk" //TODO delete hardcode
            )
        }
    }
}