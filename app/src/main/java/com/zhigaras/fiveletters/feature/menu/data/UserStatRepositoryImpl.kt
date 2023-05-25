package com.zhigaras.fiveletters.feature.menu.data

import com.google.firebase.database.DatabaseReference
import com.zhigaras.fiveletters.feature.menu.data.model.AttemptsDto
import com.zhigaras.fiveletters.feature.menu.data.model.UserStatDto
import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import com.zhigaras.fiveletters.feature.menu.domain.model.Achievement
import com.zhigaras.fiveletters.feature.menu.domain.model.Rank
import kotlinx.coroutines.tasks.await

class UserStatRepositoryImpl(private val database: DatabaseReference) : UserStatRepository {
    
    override suspend fun getUserStatFlow(userId: String): UserStatDto? {
        return database
            .child("users")
            .child(userId)
            .child("stat")
            .get()
            .await()
            .getValue(UserStatDto::class.java)
    }
    
    override suspend fun tempPutUserStat(userId: String) {
        database
            .child("users")
            .child(userId)
            .child("stat")
            .setValue(
                UserStatDto(
                    gamesPlayed = 3,
                    wins = 2,
                    rank = Rank.BEGINNER,
                    achievements = listOf(Achievement.FIRST_WIN, Achievement.SECOND_WIN),
                    attempts = AttemptsDto(0, 0, 3, 4, 5, 6)
                )
            ).await()
    }
}