package com.wordNote.wordNote.domain

import com.wordNote.wordNote.dto.word.WordUpdateForm
import javax.persistence.*

@Entity
class Word(

    var vocabulary : String?,
    var meaning : String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_set_id")
    val wordSet : WordSet?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,
) {
    fun changeWord(form : WordUpdateForm) {
        this.vocabulary = form.vocabulary
        this.meaning = form.meaning
    }
}