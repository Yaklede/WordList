package com.wordNote.wordNote.repository.wordSet

import com.wordNote.wordNote.domain.WordSet

interface WordSetCustomRepository {
    fun findByLoginId(loginId : String) : List<WordSet>?
}