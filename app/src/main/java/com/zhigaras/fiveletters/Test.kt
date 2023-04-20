package com.zhigaras.fiveletters

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType
import com.zhigaras.fiveletters.model.RowState


fun main() {
    
    val moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(GameState::class.java, "label")
            .withSubtype(GameState.Progress::class.java, GameState.Progress::class.java.simpleName)
            .withSubtype(GameState.Start::class.java, GameState.Start::class.java.simpleName)
            .withSubtype(GameState.InvalidWord::class.java, GameState.InvalidWord::class.java.simpleName)
            .withSubtype(GameState.Win::class.java, GameState.Win::class.java.simpleName)
            .withSubtype(GameState.Failed::class.java, GameState.Failed::class.java.simpleName)
        )
        .add(
            PolymorphicJsonAdapterFactory.of(LetterType::class.java, "label")
                .withSubtype(LetterType.Card::class.java, LetterType.Card::class.java.simpleName)
                .withSubtype(LetterType.Key::class.java, LetterType.Key::class.java.simpleName)
        )
        .add(PolymorphicJsonAdapterFactory.of(LetterState::class.java, "label")
            .withSubtype(LetterState.Default::class.java, LetterState.Default::class.java.simpleName)
            .withSubtype(LetterState.Exact::class.java, LetterState.Exact::class.java.simpleName)
            .withSubtype(LetterState.Right::class.java, LetterState.Right::class.java.simpleName)
            .withSubtype(LetterState.Wrong::class.java, LetterState.Wrong::class.java.simpleName)
            .withSubtype(LetterState.InvalidWord::class.java, LetterState.InvalidWord::class.java.simpleName)
            .withSubtype(LetterState.UserClicked::class.java, LetterState.UserClicked::class.java.simpleName)
        )
        .add(PolymorphicJsonAdapterFactory.of(RowState::class.java, "label")
            .withSubtype(RowState.Empty::class.java, RowState.Empty::class.java.simpleName)
            .withSubtype(RowState.UncheckedWord::class.java, RowState.UncheckedWord::class.java.simpleName)
            .withSubtype(RowState.InvalidWord::class.java, RowState.InvalidWord::class.java.simpleName)
            .withSubtype(RowState.Right::class.java, RowState.Right::class.java.simpleName)
            .withSubtype(RowState.Wrong::class.java, RowState.Wrong::class.java.simpleName)
            .withSubtype(RowState.NotFullRow::class.java, RowState.NotFullRow::class.java.simpleName)
        )
        .add(KotlinJsonAdapterFactory())
        .build()
    
    val adapter = moshi.adapter(GameState::class.java)
    
}



