package com.zhigaras.fiveletters

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zhigaras.fiveletters.data.Alphabet
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.model.Keyboard
import com.zhigaras.fiveletters.model.LetterFieldState
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType
import com.zhigaras.fiveletters.model.RowState
import com.zhigaras.fiveletters.model.Word


fun main() {
    
    val moshi = Moshi.Builder()
        .add(PolymorphicJsonAdapterFactory.of(LetterFieldState::class.java, "label")
            .withSubtype(LetterFieldState.NotStartedYet::class.java, LetterFieldState.NotStartedYet::class.java.simpleName)
            .withSubtype(LetterFieldState.Progress::class.java, LetterFieldState.Progress::class.java.simpleName)
            .withSubtype(LetterFieldState.Start::class.java, LetterFieldState.Start::class.java.simpleName)
            .withSubtype(LetterFieldState.InvalidWord::class.java, LetterFieldState.InvalidWord::class.java.simpleName)
            .withSubtype(LetterFieldState.Win::class.java, LetterFieldState.Win::class.java.simpleName)
            .withSubtype(LetterFieldState.Failed::class.java, LetterFieldState.Failed::class.java.simpleName)
        )
        .add(PolymorphicJsonAdapterFactory.of(RowState::class.java, "label")
            .withSubtype(RowState.Empty::class.java, RowState.Empty::class.java.simpleName)
            .withSubtype(RowState.UncheckedWord::class.java, RowState.UncheckedWord::class.java.simpleName)
            .withSubtype(RowState.InvalidWord::class.java, RowState.InvalidWord::class.java.simpleName)
            .withSubtype(RowState.Right::class.java, RowState.Right::class.java.simpleName)
            .withSubtype(RowState.Wrong::class.java, RowState.Wrong::class.java.simpleName)
            .withSubtype(RowState.NotFullRow::class.java, RowState.NotFullRow::class.java.simpleName)
        )
        .add(PolymorphicJsonAdapterFactory.of(LetterState::class.java, "label")
            .withSubtype(LetterState.Empty::class.java, LetterState.Empty::class.java.simpleName)
            .withSubtype(LetterState.Default::class.java, LetterState.Default::class.java.simpleName)
            .withSubtype(LetterState.Exact::class.java, LetterState.Exact::class.java.simpleName)
            .withSubtype(LetterState.Right::class.java, LetterState.Right::class.java.simpleName)
            .withSubtype(LetterState.Wrong::class.java, LetterState.Wrong::class.java.simpleName)
            .withSubtype(LetterState.InvalidWord::class.java, LetterState.InvalidWord::class.java.simpleName)
            .withSubtype(LetterState.UserClicked::class.java, LetterState.UserClicked::class.java.simpleName)
        )
        .add(PolymorphicJsonAdapterFactory.of(LetterType::class.java, "label")
            .withSubtype(LetterType.Card::class.java, LetterType.Card::class.java.simpleName)
            .withSubtype(LetterType.Key::class.java, LetterType.Key::class.java.simpleName)
        )
        .add(PolymorphicJsonAdapterFactory.of(Keyboard::class.java, "label")
            .withSubtype(Keyboard.Default::class.java, Keyboard.Default::class.java.simpleName)
            .withSubtype(Keyboard.Progress::class.java, Keyboard.Progress::class.java.simpleName))
        .add(PolymorphicJsonAdapterFactory.of(Alphabet::class.java, "label")
            .withSubtype(Alphabet.Ru::class.java, Alphabet.Ru::class.java.simpleName)
            .withSubtype(Alphabet.En::class.java, Alphabet.En::class.java.simpleName))
        .add(KotlinJsonAdapterFactory())
        .build()
    
    val adapter = moshi.adapter(GameState::class.java)
    
    val gameState = GameState(
        letterFieldState = LetterFieldState.NotStartedYet(),
        keyboard = Keyboard.Default(Alphabet.Ru()),
        columnCursor = 0,
        rowCursor = 0,
        origin = Word(1, "asdaa", false, 0)
    )
    
    val json = adapter.toJson(gameState)
    println(json)
    val jsonNew = json.dropLast(10)
    val state = adapter.fromJson(jsonNew)
    println(state)
    
    
}



