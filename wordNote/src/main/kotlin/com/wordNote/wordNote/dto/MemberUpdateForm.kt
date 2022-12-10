package com.wordNote.wordNote.dto

import com.wordNote.wordNote.domain.Member

data class MemberUpdateForm(
    val password : String,
    val name : String,
) {
}