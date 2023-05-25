package com.zhigaras.fiveletters.feature.menu.domain

import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    
    suspend fun getCurrentUser(): FirebaseUser?
    
}