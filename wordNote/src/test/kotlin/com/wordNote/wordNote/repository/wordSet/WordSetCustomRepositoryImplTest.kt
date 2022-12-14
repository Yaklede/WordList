package com.wordNote.wordNote.repository.wordSet

import com.wordNote.wordNote.domain.Member
import com.wordNote.wordNote.domain.WordSet
import com.wordNote.wordNote.repository.MemberRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class WordSetCustomRepositoryImplTest @Autowired constructor(
    private val wordSetRepository: WordSetRepository,
    private val memberRepository: MemberRepository,
) {
    @Test
    fun findWordSetListByMemberLoginId() {
        //given
        val member = Member("test","pass","name")
        memberRepository.save(member)
        wordSetRepository.save(WordSet("title","desc",member))
        //when
        val findWordSetList = wordSetRepository.findByLoginId(member.loginId)
        val findMember = memberRepository.findByLoginId(member.loginId)
        //then
        assertThat(findWordSetList?.size).isEqualTo(1)
        assertThat(findWordSetList?.get(0)?.member?.id).isEqualTo(findMember?.id)
    }
}