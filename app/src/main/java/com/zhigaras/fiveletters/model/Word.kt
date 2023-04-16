package com.zhigaras.fiveletters.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_ru")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val word: String,
    val solvedByUser: Boolean,
    val attempts: Int
)