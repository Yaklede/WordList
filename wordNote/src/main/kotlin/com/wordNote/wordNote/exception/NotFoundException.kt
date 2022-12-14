package com.wordNote.wordNote.exception

open class NotFoundException : RuntimeException {
    constructor(message: String?) : super(message)
}