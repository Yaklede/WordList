package com.wordNote.wordNote.service

import com.wordNote.wordNote.domain.Member
import com.wordNote.wordNote.domain.WordSet
import com.wordNote.wordNote.dto.WordSetCreateForm
import com.wordNote.wordNote.dto.WordSetUpdateForm
import com.wordNote.wordNote.exception.MemberNotFoundException
import com.wordNote.wordNote.exception.WordSetNotFoundException
import com.wordNote.wordNote.repository.MemberRepository
import com.wordNote.wordNote.repository.WordSetRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WordSetService(
    private val memberService: MemberService,
    private val wordSetRepository: WordSetRepository,
) {
    @Transactional
    fun create(memberId : Long?, form : WordSetCreateForm) : Long? {
        val member = memberService.findById(memberId)
        val wordSet = WordSet(form.title,form.description,member)
        wordSet.settingMemberWordSetList(wordSet)
        wordSetRepository.save(wordSet)
        return wordSet.id
    }

    @Transactional
    fun delete(wordSetId: Long?) {
        val findWordSet = getWordSetById(wordSetId)
        wordSetRepository.delete(findWordSet)
    }
    @Transactional
    fun update(wordSetId : Long?, form : WordSetUpdateForm) : Long? {
        val findWordSet = getWordSetById(wordSetId)
        findWordSet?.update(form)
        return findWordSet?.id
    }
    fun findById(wordSetId : Long?) : WordSet? {
        return getWordSetById(wordSetId)
    }
    private fun getWordSetById(wordSetId: Long?) : WordSet? {
        return wordSetRepository.findById(wordSetId).orElse(null) ?: throw WordSetNotFoundException()
    }

    fun findByTitle(title : String) : WordSet? {
        return wordSetRepository.findByTitle(title) ?: throw WordSetNotFoundException()
    }
    fun findAll() : List<WordSet>? {
        return wordSetRepository.findAll()
    }
}