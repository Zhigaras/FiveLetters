package com.zhigaras.fiveletters.feature.play.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class ProgressState {
    JUST_STARTED,
    PROGRESS,
    ENDED
}