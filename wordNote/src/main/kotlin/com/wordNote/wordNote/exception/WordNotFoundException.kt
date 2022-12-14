package com.wordNote.wordNote.exception

import org.aspectj.weaver.ast.Not

class WordNotFoundException : NotFoundException {
    constructor() : super("단어를 찾을 수 없습니다.")
}