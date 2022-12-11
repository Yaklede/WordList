package com.wordNote.wordNote.service

import com.wordNote.wordNote.domain.Member
import com.wordNote.wordNote.domain.WordSet
import com.wordNote.wordNote.dto.WordSetCreateForm
import com.wordNote.wordNote.dto.WordSetUpdateForm
import com.wordNote.wordNote.exception.WordSetNotFoundException
import com.wordNote.wordNote.repository.MemberRepository
import com.wordNote.wordNote.repository.WordSetRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class WordSetServiceTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val wordSetRepository: WordSetRepository,
    private val wordSetService: WordSetService,
) {
    @Test
    fun wordSetCreateTest() {
        //given
        val member = memberRepository.save(Member("test","test","test"))
        val form = WordSetCreateForm("title","description")
        val wordSetId = wordSetService.create(member.id, form)
        val wordSet = wordSetService.findById(wordSetId)
        //when
        val findWordSet = wordSetRepository.findByTitle("title")

        //then
        assertThat(wordSet?.title).isEqualTo(findWordSet?.title)
        assertThat(wordSet?.member).isEqualTo(findWordSet?.member)
        assertThat(wordSet?.description).isEqualTo(findWordSet?.description)
    }

    @Test
    fun wordSetByTitleNotFoundException() {
        //given
        val member = memberRepository.save(Member("test","test","test"))
        val wordSet = wordSetRepository.save(WordSet("title","description",member))
        //when
        val assertThrow = assertThrows(WordSetNotFoundException::class.java) {
            wordSetService.findByTitle("ErrorTitle")
        }
        //then
        assertThat(assertThrow.message).isEqualTo("학습세트를 찾을 수 없습니다.")
    }
    @Test
    fun findAll() {
        //given
        val member = memberRepository.save(Member("test","test","test"))
        wordSetRepository.save(WordSet("title","description",member))
        wordSetRepository.save(WordSet("title","description",member))

        //when
        val wordSets = wordSetService.findAll()

        //then
        assertThat(wordSets?.size).isEqualTo(2)
    }

    /**
     * mutableListOf 로 초기화 해주지 않으면 값이 들어가지 않음
     */
    @Test
    fun memberSettingWordSet() {
        //given
        val member = memberRepository.save(Member("test","test","test"))
        val form = WordSetCreateForm("title","description")
        val wordSetId = wordSetService.create(member.id,form)

        //when
        val wordSet = wordSetService.findById(wordSetId)
        val findMember = memberRepository.findByLoginId("test")
        //then
        assertThat(wordSet?.title).isEqualTo(findMember?.wordSets?.get(0)?.title)

    }

    @Test
    fun updateTest() {
        //given
        val member = memberRepository.save(Member("test","test","test"))
        val createForm = WordSetCreateForm("title","description")
        val wordSetId = wordSetService.create(member.id,createForm)

        //when
        val findWordSet = wordSetService.findById(wordSetId)
        val updateForm = WordSetUpdateForm("updateTitle","updateDescription")
        val updatedId = wordSetService.update(findWordSet?.id,updateForm)
        val updatedWordSet = wordSetService.findById(updatedId)
        //then
        assertThat(updatedWordSet?.title).isEqualTo("updateTitle")
        assertThat(updatedWordSet?.description).isEqualTo("updateDescription")
    }

    @Test
    fun deleteTest() {
        //given
        val member = memberRepository.save(Member("test","test","test"))
        val createForm = WordSetCreateForm("title","description")
        val wordSetId = wordSetService.create(member.id,createForm)
        //when
        wordSetService.delete(wordSetId)
        val assertThrows = assertThrows(WordSetNotFoundException::class.java) {
            wordSetService.findById(wordSetId)
        }
        //then
        assertThat(assertThrows.message).isEqualTo("학습세트를 찾을 수 없습니다.")
    }

}