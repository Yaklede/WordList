package com.wordNote.wordNote.dto

data class MemberCreateForm(
    val loginId : String,
    val password : String,
    val name : String,
) {
}