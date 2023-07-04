package com.zhigaras.fiveletters.feature.play.domain

import com.zhigaras.fiveletters.database.MainDao

interface MainRepository : MainDao, UserStatInteract.Write