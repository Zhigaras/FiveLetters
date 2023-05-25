package com.zhigaras.fiveletters.feature.menu.domain.model

data class UserStat(
    val gamesPlayed: Int,
    val wins: Int,
    val rank: Rank,
    val achievements: List<Achievement>,
    val attempts: Attempts
) {
    val winRate get() = String.format("%.1f", (wins * 100 / gamesPlayed)) + "%"
    
    companion object {
        val initial = UserStat(
            gamesPlayed = 0,
            wins = 0,
            rank = Rank.BEGINNER,
            achievements = emptyList(),
            attempts = Attempts.initial
        )
    }
}