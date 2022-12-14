package com.wordNote.wordNote.exception

class MemberNotFoundException : NotFoundException {
    constructor() : super("회원을 찾을 수 없습니다.")
}