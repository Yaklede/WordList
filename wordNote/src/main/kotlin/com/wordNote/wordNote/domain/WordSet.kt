package com.wordNote.wordNote.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wordNote.wordNote.dto.wordSet.WordSetUpdateForm
import javax.persistence.*

@Entity
class WordSet(

    var title: String?,
    var description: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    var member : Member?,

    @OneToMany(mappedBy = "wordSet", cascade = [CascadeType.ALL])
    @Column(name = "word_id")
    var words: MutableList<Word>? = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    fun settingMemberWordSetList(wordSet: WordSet) {
        this.member?.wordSets?.add(wordSet)
    }

    fun settingWordList(words : MutableList<Word>?) {
        this.words = words
    }

    fun update(form : WordSetUpdateForm) {
        this.title = form.title
        this.description = form.description
    }
}