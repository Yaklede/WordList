package com.wordNote.wordNote.dto.word

import com.wordNote.wordNote.domain.Word


data class WordDTO (
    val wordId : Long?,
    val vocabulary: String?,
    val meaning : String?
) {
    constructor(word : Word?) : this(word?.id,word?.vocabulary,word?.meaning)
}