package com.wordNote.wordNote.repository.wordSet

import com.wordNote.wordNote.domain.WordSet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WordSetRepository : JpaRepository<WordSet,Long>,WordSetCustomRepository {
    fun findByTitle(title : String) : WordSet?
}