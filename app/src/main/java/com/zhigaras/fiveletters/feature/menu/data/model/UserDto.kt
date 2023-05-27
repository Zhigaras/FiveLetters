package com.zhigaras.fiveletters.feature.menu.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserDto( // todo remove if useless
    val id: String = "",
    val name: String = "",
    val email: String = ""
)
