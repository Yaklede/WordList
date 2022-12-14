package com.wordNote.wordNote.controller

import com.wordNote.wordNote.domain.WordSet
import com.wordNote.wordNote.dto.member.MemberCreateForm
import com.wordNote.wordNote.dto.wordSet.WordSetCreateForm
import com.wordNote.wordNote.dto.wordSet.WordSetUpdateForm
import com.wordNote.wordNote.service.MemberService
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

@RestController
@RequestMapping("/wordSet")
class WordSetController(
    private val wordSetService: WordSetService,
) {

    @PostConstruct
    fun init() {
        wordSetService.create(1L,WordSetCreateForm("testTitle","desc"))
    }

    @GetMapping("/list/{loginId}")
    fun wordSetList(@PathVariable("loginId") loginId : String) : List<WordSet>? {
        return wordSetService.findWordSetListByLoginId(loginId)
    }

    @PostMapping("/create/{memberId}")
    fun create(@PathVariable("memberId") memberId : Long ,@ModelAttribute("wordSetCreateForm") form : WordSetCreateForm) {
        wordSetService.create(memberId,form)
    }

    @PatchMapping("/update/{wordSetId}")
    fun update(@PathVariable("wordSetId") wordSetId : Long , @ModelAttribute("wordSetUpdateForm") form : WordSetUpdateForm){
        wordSetService.update(wordSetId,form)
    }

    @DeleteMapping("/delete/{wordSetId}")
    fun delete(@PathVariable("wordSetId") wordSetId: Long) {
        wordSetService.delete(wordSetId)
    }

}