package com.zhigaras.fiveletters.model

import com.squareup.moshi.JsonClass

abstract class UserStat {
    
    abstract val wins: Int
    abstract val progress: Float
    abstract val games: Int
    abstract val averageAttempts: Float
    
    val formattedProgress get() = String.format("%.1f", (progress * 100)) + "%"
    val formattedAttempts get() = String.format("%.1f", averageAttempts)
    
    @JsonClass(generateAdapter = true)
    data class Base(
        override val wins: Int,
        override val progress: Float,
        override val games: Int,
        override val averageAttempts: Float
    ) : UserStat()
    
    @JsonClass(generateAdapter = true)
    class Empty : UserStat() {
        override val wins: Int = 0
        override val progress: Float = 0f
        override val games: Int = 0
        override val averageAttempts: Float = 0f
    }
}