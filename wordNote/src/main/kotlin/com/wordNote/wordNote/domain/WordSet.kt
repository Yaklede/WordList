package com.wordNote.wordNote.domain

import javax.persistence.*

@Entity
class WordSet(

    val title : String?,
    val description : String?,

    @ManyToOne(fetch = FetchType.LAZY)
    val member : Member,

    @OneToMany(mappedBy = "wordSet")
    @Column(name = "word_id")
    val word : List<Word>,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,
) {
}