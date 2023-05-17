package com.zhigaras.fiveletters.model.play

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class ProgressState {
    JUST_STARTED,
    PROGRESS,
    ENDED
}