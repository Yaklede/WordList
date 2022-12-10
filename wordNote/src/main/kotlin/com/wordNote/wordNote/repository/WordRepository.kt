package com.wordNote.wordNote.repository

import com.wordNote.wordNote.domain.Word
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WordRepository : JpaRepository<Word,Long> {
}