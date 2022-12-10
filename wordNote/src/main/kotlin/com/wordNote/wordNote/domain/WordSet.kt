package com.wordNote.wordNote.domain

import com.wordNote.wordNote.dto.WordSetUpdateForm
import javax.persistence.*

@Entity
class WordSet(

    var title: String?,
    var description: String?,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id")
    var member : Member?,

    @OneToMany(mappedBy = "wordSet", cascade = [CascadeType.ALL])
    @Column(name = "word_id")
    val word: MutableList<Word>? = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    fun settingMemberWordSetList(wordSet: WordSet) {
        this.member?.wordSets?.add(wordSet)
    }

    fun update(form : WordSetUpdateForm) {
        this.title = form.title
        this.description = form.description
    }
}