package com.wordNote.wordNote.dto.member

import com.wordNote.wordNote.domain.Member

data class MemberUpdateForm(
    val password : String,
    val name : String,
) {
}