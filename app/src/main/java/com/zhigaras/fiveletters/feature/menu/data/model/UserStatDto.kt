package com.zhigaras.fiveletters.feature.menu.data.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.zhigaras.fiveletters.feature.menu.domain.model.Achievement
import com.zhigaras.fiveletters.feature.menu.domain.model.Rank
import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat

@IgnoreExtraProperties
data class UserStatDto(
    val gamesPlayed: Int = 0,
    val wins: Int = 0,
    val rank: Rank = Rank.BEGINNER,
    val achievements: List<Achievement> = emptyList(),
    val attempts: List<Int> = List(6) { 0 }
) {
    @Exclude
    fun map() = UserStat.Base(
        gamesPlayed = gamesPlayed,
        wins = wins,
        rank = rank,
        achievements = achievements,
        attempts = attempts
    )
}