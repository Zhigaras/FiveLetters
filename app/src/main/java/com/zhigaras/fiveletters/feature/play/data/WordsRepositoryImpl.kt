package com.zhigaras.fiveletters.feature.play.data

import com.google.firebase.database.DatabaseReference
import com.zhigaras.fiveletters.feature.play.domain.WordsRepository
import kotlinx.coroutines.tasks.await

class WordsRepositoryImpl(private val database: DatabaseReference) : WordsRepository {
    
//    private lateinit var db: List<*>
    
//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            Log.d("AAA", "start init")
//            db = database.child(RU_WORDS_PATH).get().await().value as List<*> //todo refactor
//            Log.d("AAA", "finish init")
//
//        }
//    }
    
    override suspend fun getNewWord(): String {
        return (database.child(RU_WORDS_PATH).get().await().value as List<*>)[1] as String
    }
    
    override suspend fun isWordValid(word: String): Boolean {
        return (database.child(RU_WORDS_PATH).get().await().value as List<*>).contains(word)
    }
    
    companion object {
        const val RU_WORDS_PATH = "wordsRu"
        const val EN_WORDS_PATH = "wordsEn"
    }
}