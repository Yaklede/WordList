package com.wordNote.wordNote.service

import com.wordNote.wordNote.dto.member.MemberCreateForm
import com.wordNote.wordNote.dto.word.WordCreateForm
import com.wordNote.wordNote.dto.wordSet.WordSetCreateForm
import com.wordNote.wordNote.dto.word.WordUpdateForm
import com.wordNote.wordNote.exception.WordNotFoundException
import com.wordNote.wordNote.repository.WordRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class WordServiceTest @Autowired constructor(
    private val memberService: MemberService,
    private val wordService: WordService,
    private val wordSetService: WordSetService,
    private val wordRepository: WordRepository,
) {
    @BeforeEach
    fun init() {
        val memberForm = MemberCreateForm("id","pass","name")
        val memberId = memberService.join(memberForm)
        val wordSetForm = WordSetCreateForm("title","desc")
        wordSetService.create(memberId,wordSetForm)
    }

    @Test
    fun createTest() {
        //given
        val wordFormList : MutableList<WordCreateForm> = mutableListOf()
        val wordForm = WordCreateForm("test","테스트")
        wordFormList.add(wordForm)
        val wordSet = wordSetService.findByTitle("title")
        //when
        wordService.create(wordSet?.id,wordFormList)
        //then
        val savedWordSet = wordSetService.findById(wordSet?.id)
        val findAll = wordRepository.findAll()
        assertThat(findAll.get(0).meaning).isEqualTo("테스트")
        assertThat(savedWordSet?.words?.get(0)?.meaning).isEqualTo("테스트")
    }

    @Test
    fun findAllByWordSetIdTest() {
        //given
        val wordFormList : MutableList<WordCreateForm> = mutableListOf()
        val wordForm = WordCreateForm("test","테스트")
        wordFormList.add(wordForm)
        val wordSet = wordSetService.findByTitle("title")
        //when
        wordService.create(wordSet?.id,wordFormList)
        val findAll = wordService.findAllByWordSetId(wordSet?.id)
        //then
        assertThat(findAll?.get(0)?.vocabulary).isEqualTo(wordForm.vocabulary)
        assertThat(findAll?.get(0)?.meaning).isEqualTo(wordForm.meaning)
    }
    @Test
    fun wordExceptionTest() {
        val assertThrows = assertThrows(WordNotFoundException::class.java) {
            wordService.findById(1L)
        }
        assertThat(assertThrows.message).isEqualTo("단어를 찾을 수 없습니다.")
    }

    @Test
    fun updateTest() {
        //given
        val wordFormList : MutableList<WordCreateForm> = mutableListOf()
        val wordForm = WordCreateForm("test","테스트")
        wordFormList.add(wordForm)
        val wordSet = wordSetService.findByTitle("title")
        wordService.create(wordSet?.id,wordFormList)
        val wordId = wordSet?.words?.get(0)?.id

        //when
        val beforeWord = wordService.findById(wordId)
        assertThat(beforeWord?.vocabulary).isEqualTo(wordForm.vocabulary)
        assertThat(beforeWord?.meaning).isEqualTo(wordForm.meaning)
        val updateForm = WordUpdateForm("update","업데이트")
        wordService.update(wordId,updateForm)
        val afterWord = wordService.findById(wordId)

        //then


        assertThat(afterWord?.vocabulary).isEqualTo(updateForm.vocabulary)
        assertThat(afterWord?.meaning).isEqualTo(updateForm.meaning)

    }

    @Test
    fun deleteTest() {
        //given
        val wordFormList : MutableList<WordCreateForm> = mutableListOf()
        val wordForm = WordCreateForm("test","테스트")
        wordFormList.add(wordForm)
        val wordSet = wordSetService.findByTitle("title")
        wordService.create(wordSet?.id,wordFormList)
        val wordId = wordSet?.words?.get(0)?.id
        //when
        wordService.delete(wordId)
        val assertThrow = assertThrows(WordNotFoundException::class.java) {
            wordService.findById(wordId)
        }
        //then
        assertThat(assertThrow.message).isEqualTo("단어를 찾을 수 없습니다.")
    }

    @Test
    fun deleteCascadeTest() {
        //given
        val wordFormList : MutableList<WordCreateForm> = mutableListOf()
        val wordForm = WordCreateForm("test","테스트")
        wordFormList.add(wordForm)
        val wordSet = wordSetService.findByTitle("title")
        wordService.create(wordSet?.id,wordFormList)
        val wordId = wordSet?.words?.get(0)?.id
        //when
        wordSetService.delete(wordSet?.id)
        //then
        val findAll = wordService.findAllByWordSetId(wordSet?.id)
        val member = memberService.findByLoginId("id")
        assertThat(findAll).isEmpty()
        assertThat(member).isNotNull
    }
    @Test
    fun deleteWordCascadeTest() {
        //given
        val wordFormList : MutableList<WordCreateForm> = mutableListOf()
        val wordForm = WordCreateForm("test","테스트")
        val wordForm2 = WordCreateForm("test","테스트")
        wordFormList.add(wordForm)
        wordFormList.add(wordForm2)
        val wordSet = wordSetService.findByTitle("title")
        wordService.create(wordSet?.id,wordFormList)
        val wordId = wordSet?.words?.get(0)?.id
        //when
        wordService.delete(wordId)
        //then
        val findAll = wordService.findAllByWordSetId(wordSet?.id)
        val member = memberService.findByLoginId("id")
        assertThat(findAll).isNotEmpty
        assertThat(member).isNotNull
    }
}