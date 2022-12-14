package com.wordNote.wordNote.dto.wordSet

import com.wordNote.wordNote.domain.Word
import com.wordNote.wordNote.domain.WordSet
import com.wordNote.wordNote.dto.word.WordDTO

data class WordSetDTO(
    val memberId : Long?,
    val wordSetId : Long?,
    val title : String?,
    val description : String?,
    val wordList : List<WordDTO>?
) {
    constructor(wordSet : WordSet?) : this(wordSet?.member?.id,wordSet?.id,wordSet?.title,wordSet?.description,wordSet?.words?.map { word: Word -> WordDTO(word) })
}