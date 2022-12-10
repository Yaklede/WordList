package com.wordNote.wordNote.domain

import javax.persistence.*

@Entity
class Word(

    val vocabulary : String?,
    val meaning : String?,

    @ManyToOne(fetch = FetchType.LAZY)
    val wordSet : WordSet?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,
) {
}