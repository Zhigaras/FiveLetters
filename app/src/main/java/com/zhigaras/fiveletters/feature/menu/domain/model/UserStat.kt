package com.zhigaras.fiveletters.feature.menu.domain.model

abstract class UserStat {
    abstract val attempts: List<Int>
    abstract val gamesPlayed: Int
    abstract val wins: Int
    abstract val rank: Rank
    abstract val achievements: List<Achievement>
    
    val averageAttempts
        get() =
            if (attempts.sum() == 0) 0f
            else attempts.mapIndexed { index, i -> i * (index + 1) }.sum()
                .toFloat() / attempts.sum()
    
    fun formattedAverageAttempts() = String.format("%.1f", averageAttempts)
    
    data class Base(
        override val gamesPlayed: Int,
        override val wins: Int,
        override val rank: Rank,
        override val achievements: List<Achievement>,
        override val attempts: List<Int>
    ) : UserStat() {
        
        val winRate get() = String.format("%.1f", (wins * 100 / gamesPlayed)) + "%"
        
        companion object {
            val initial = Base(
                gamesPlayed = 0,
                wins = 0,
                rank = Rank.BEGINNER,
                achievements = emptyList(),
                attempts = List(6) { 0 }
            )
        }
    }
    
    class Test(override val attempts: List<Int>) : UserStat() {
        override val gamesPlayed: Int = 0
        override val wins: Int = 0
        override val rank: Rank = Rank.BEGINNER
        override val achievements: List<Achievement> = emptyList()
    }
}