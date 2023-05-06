package com.zhigaras.fiveletters.model

import com.squareup.moshi.JsonClass

abstract class Alphabet {
    
    protected abstract val alphabet: List<List<Char>>
    
    fun getKeyboardAlphabet(): List<List<Char>> = alphabet
    
    @JsonClass(generateAdapter = true)
    class Ru : Alphabet() {
        
        override val alphabet = listOf(
            listOf('й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'х', 'ъ'),
            listOf('ф', 'ы', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'ж', 'э'),
            listOf('я', 'ч', 'с', 'м', 'и', 'т', 'ь', 'б', 'ю')
        )
    }
    
    @JsonClass(generateAdapter = true)
    class En : Alphabet() {
        override val alphabet = listOf(
            listOf('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'),
            listOf('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'),
            listOf('z', 'x', 'c', 'v', 'b', 'n', 'm')
        )
    }
}
