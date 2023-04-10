package com.zhigaras.fiveletters.data

interface Alphabet {
    
    fun getKeyboardAlphabet(): Array<Array<Char>>
    
    abstract class Base : Alphabet {
        
        protected abstract val alphabet: Array<Array<Char>>
        
        override fun getKeyboardAlphabet(): Array<Array<Char>> = alphabet
        
        class Ru : Base() {
            
            override val alphabet = arrayOf(
                arrayOf('й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'х', 'ъ'),
                arrayOf('ф', 'ы', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'ж', 'э'),
                arrayOf('я', 'ч', 'с', 'м', 'и', 'т', 'ь', 'б', 'ю')
            )
        }
        
        class En : Base() {
            override val alphabet = arrayOf(
                arrayOf('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'),
                arrayOf('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'),
                arrayOf('z', 'x', 'c', 'v', 'b', 'n', 'm')
            )
        }
    }
}