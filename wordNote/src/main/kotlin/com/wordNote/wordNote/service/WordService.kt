package com.wordNote.wordNote.service

import com.wordNote.wordNote.domain.Word
import com.wordNote.wordNote.dto.word.WordCreateForm
import com.wordNote.wordNote.dto.word.WordUpdateForm
import com.wordNote.wordNote.exception.WordNotFoundException
import com.wordNote.wordNote.repository.WordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WordService(
    private val wordSetService: WordSetService,
    private val wordRepository: WordRepository,
) {
    @Transactional
    fun create(wordSetId : Long? , wordFormList : MutableList<WordCreateForm>) {
        val wordSet = wordSetService.findById(wordSetId)
        wordFormList.forEach { wordForm ->
            val word = Word(wordForm.vocabulary,wordForm.meaning,wordSet)
            wordRepository.save(word)
        }
        val savedWordList = wordRepository.findAllByWordSetId(wordSetId)
        wordSet?.settingWordList(savedWordList)
    }

    fun findAllByWordSetId(wordSetId: Long?) : MutableList<Word>? {
        val findList = wordRepository.findAllByWordSetId(wordSetId)
        if(findList!!.isEmpty()) {
            return throw WordNotFoundException()
        }
        return findList
    }
    fun findById(wordId : Long?) : Word? {
        return getWordById(wordId)
    }
    @Transactional
    fun delete(wordId: Long?) {
        val word = getWordById(wordId)
        wordRepository.delete(word)
    }

    @Transactional
    fun update(wordId: Long?, updateWordForm : WordUpdateForm) : Long? {
        val word = getWordById(wordId)
        word?.changeWord(updateWordForm)
        return word?.id
    }

    @Transactional
    fun add(wordSetId: Long?, wordFormList: MutableList<WordCreateForm>) {
        val wordSet = wordSetService.findById(wordSetId)
        wordFormList.forEach { wordCreateForm ->
            wordSet?.words?.add(Word(wordCreateForm.vocabulary,wordCreateForm.meaning,wordSet))
        }
    }
    private fun getWordById(wordId: Long?) : Word? {
        return wordRepository.findById(wordId).orElse(null) ?: throw WordNotFoundException()
    }
}