package com.zhigaras.fiveletters.feature.menu.data.model

import com.zhigaras.fiveletters.feature.menu.domain.model.Attempts

data class AttemptsDto(
    val firstLine: Int = 0,
    val secondLine: Int = 0,
    val thirdLine: Int = 0,
    val fourthLine: Int = 0,
    val fifthLine: Int = 0,
    val sixthLine: Int = 0
) {
    fun map() = Attempts(firstLine, secondLine, thirdLine, fourthLine, fifthLine, sixthLine)
}