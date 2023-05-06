package com.zhigaras.fiveletters.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "words_ru")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val word: String,
    val solvedByUser: Boolean,
    val attempts: Int,
) {
    companion object {
        val mock = Word(1, "start", false, 0)
        val rulesMock = Word(1, "песня", false, 0)
    }
}