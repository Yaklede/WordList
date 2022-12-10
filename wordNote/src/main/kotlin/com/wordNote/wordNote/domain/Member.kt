package com.wordNote.wordNote.domain

import com.wordNote.wordNote.dto.MemberUpdateForm
import javax.persistence.*

@Entity
class Member(
    val loginId : String,
    var password : String,
    var name : String,

    @OneToMany(mappedBy = "member")
    @Column(name = "word_set_id")
    val wordSet : List<WordSet>,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,
) {
    fun update(form : MemberUpdateForm) {
        this.password = form.password
        this.name = form.name
    }
}