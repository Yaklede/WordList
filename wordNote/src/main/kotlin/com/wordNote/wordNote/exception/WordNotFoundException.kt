package com.wordNote.wordNote.exception

class WordNotFoundException : RuntimeException {
    constructor() : super("단어를 찾을 수 없습니다.")
}