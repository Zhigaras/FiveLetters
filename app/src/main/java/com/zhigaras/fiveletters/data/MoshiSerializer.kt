package com.zhigaras.fiveletters.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonAdapter.Factory
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.zhigaras.fiveletters.model.Alphabet
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.model.Keyboard
import com.zhigaras.fiveletters.model.LetterFieldState
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType
import com.zhigaras.fiveletters.model.RowState
import java.io.IOException
import java.lang.reflect.Type

interface MoshiSerializer {
    
    fun serialize(gameState: GameState): String
    
    fun deserialize(json: String): GameState?
    
    class Base : MoshiSerializer {
    
        private val moshi = Moshi.Builder()
            .add(PolymorphicJsonAdapterFactory.of(LetterFieldState::class.java, "label")
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
            ).add(PolymorphicJsonAdapterFactory.of(Keyboard::class.java, "label")
                .withSubtype(Keyboard.Default::class.java, Keyboard.Default::class.java.simpleName)
                .withSubtype(Keyboard.Progress::class.java, Keyboard.Progress::class.java.simpleName)
            )
            .add(PolymorphicJsonAdapterFactory.of(Alphabet::class.java, "label")
                .withSubtype(Alphabet.Ru::class.java, Alphabet.Ru::class.java.simpleName)
                .withSubtype(Alphabet.En::class.java, Alphabet.En::class.java.simpleName))
            .add(MoshiArrayListJsonAdapter.FACTORY)
            .build()
    
        @OptIn(ExperimentalStdlibApi::class)
        private val adapter = moshi.adapter<GameState>()
        
        override fun serialize(gameState: GameState): String = adapter.toJson(gameState)
        
        override fun deserialize(json: String): GameState? {
            return adapter.fromJson(json)
        }
    }
}

abstract class MoshiArrayListJsonAdapter<C : MutableCollection<T>?, T> private constructor(
    private val elementAdapter: JsonAdapter<T>
) :
    JsonAdapter<C>() {
    abstract fun newCollection(): C
    
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): C {
        val result = newCollection()
        reader.beginArray()
        while (reader.hasNext()) {
            result?.add(elementAdapter.fromJson(reader)!!)
        }
        reader.endArray()
        return result
    }
    
    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: C?) {
        writer.beginArray()
        for (element in value!!) {
            elementAdapter.toJson(writer, element)
        }
        writer.endArray()
    }
    
    override fun toString(): String {
        return "$elementAdapter.collection()"
    }
    
    companion object {
        val FACTORY = Factory { type, annotations, moshi ->
            val rawType = Types.getRawType(type)
            if (annotations.isNotEmpty()) return@Factory null
            if (rawType == ArrayList::class.java) {
                return@Factory newArrayListAdapter<Any>(
                    type,
                    moshi
                ).nullSafe()
            }
            null
        }
        
        private fun <T> newArrayListAdapter(
            type: Type,
            moshi: Moshi
        ): JsonAdapter<MutableCollection<T>> {
            val elementType =
                Types.collectionElementType(
                    type,
                    MutableCollection::class.java
                )
            
            val elementAdapter: JsonAdapter<T> = moshi.adapter(elementType)
            
            return object :
                MoshiArrayListJsonAdapter<MutableCollection<T>, T>(elementAdapter) {
                override fun newCollection(): MutableCollection<T> {
                    return ArrayList()
                }
            }
        }
    }
}