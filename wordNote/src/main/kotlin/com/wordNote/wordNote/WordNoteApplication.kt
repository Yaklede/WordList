package com.wordNote.wordNote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WordNoteApplication

fun main(args: Array<String>) {
	runApplication<WordNoteApplication>(*args)
}
