package com.wordNote.wordNote.dto.member

data class MemberCreateForm(
    val loginId : String,
    val password : String,
    val name : String,
) {
}