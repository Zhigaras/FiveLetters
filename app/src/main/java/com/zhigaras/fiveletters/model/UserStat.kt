package com.zhigaras.fiveletters.model

data class UserStat(
    val wins: Int,
    val progress: Float,
    val games: Int,
    val averageAttempts: Float
) {
    val formattedProgress get() = String.format("%.1f", (progress * 100)) + "%"
    val formattedAttempts = String.format("%.1f", averageAttempts)
}