package com.wordNote.wordNote.dto.word

data class WordListForm(
    val wordList : MutableList<WordCreateForm> = mutableListOf()
) {
}