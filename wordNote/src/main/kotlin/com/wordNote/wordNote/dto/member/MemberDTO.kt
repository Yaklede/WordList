package com.wordNote.wordNote.dto.member

import com.wordNote.wordNote.domain.Member

data class MemberDTO(
    val id : Long?,
    val loginId : String,
    val password : String,
) {
    constructor(member : Member) : this(member.id,member.loginId,member.password)
}