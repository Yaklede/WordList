package com.wordNote.wordNote.controller

import com.wordNote.wordNote.domain.Word
import com.wordNote.wordNote.dto.word.WordCreateForm
import com.wordNote.wordNote.dto.word.WordListForm
import com.wordNote.wordNote.dto.word.WordUpdateForm
import com.wordNote.wordNote.service.WordService
import com.wordNote.wordNote.service.WordSetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/word")
class WordController(
    private val wordService: WordService,
) {
    @GetMapping("/list/{wordSetId}")
    fun list(@PathVariable("wordSetId") wordSetId : Long) : MutableList<Word>? {
        return wordService.findAllByWordSetId(wordSetId)
    }

    /**
     * 데이터를 List 형태로 받기 위해서 JSON 형태로 전송
     * Json 데이터를 객체로 받기 위해 @RequestBody를 사용
     */
    @PostMapping("/create/{wordSetId}")
    fun create(@PathVariable("wordSetId") wordSetId: Long, @RequestBody wordListForm: WordListForm) {
        wordService.create(wordSetId,wordListForm.wordList)
    }

    @PatchMapping("/add/{wordSetId}")
    fun addWord(@PathVariable("wordSetId") wordSetId: Long, @RequestBody wordListForm: WordListForm) {
        wordService.add(wordSetId,wordListForm.wordList)
    }
    @PatchMapping("/update/{wordId}")
    fun update(@PathVariable("wordId") wordId : Long , @ModelAttribute("wordUpdateForm") wordUpdateForm: WordUpdateForm) {
        wordService.update(wordId,wordUpdateForm)
    }

    @DeleteMapping("/delete/{wordId}")
    fun delete(@PathVariable("wordId") wordId : Long) {
        wordService.delete(wordId)
    }

}