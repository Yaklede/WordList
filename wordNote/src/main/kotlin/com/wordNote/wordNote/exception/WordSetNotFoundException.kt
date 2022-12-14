package com.wordNote.wordNote.exception

class WordSetNotFoundException : NotFoundException {
    constructor() : super("학습세트를 찾을 수 없습니다.")
}