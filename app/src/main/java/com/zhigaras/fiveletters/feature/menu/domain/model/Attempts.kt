package com.zhigaras.fiveletters.feature.menu.domain.model

data class Attempts(
    val firstLine: Int,
    val secondLine: Int,
    val thirdLine: Int,
    val fourthLine: Int,
    val fifthLine: Int,
    val sixthLine: Int
) {
    private val averageAttempts =
        listOf(firstLine, secondLine, thirdLine, fourthLine, fifthLine, sixthLine).average()
    val formattedAttempts get() = String.format("%.1f", averageAttempts)
    
    companion object {
        val initial = Attempts(0, 0, 0, 0, 0, 0)
    }
}