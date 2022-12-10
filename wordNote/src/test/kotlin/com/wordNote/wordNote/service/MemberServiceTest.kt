package com.wordNote.wordNote.service

import com.wordNote.wordNote.domain.Member
import com.wordNote.wordNote.dto.MemberUpdateForm
import com.wordNote.wordNote.exception.MemberNotFoundException
import com.wordNote.wordNote.repository.MemberRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberServiceTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService,
) {

    @BeforeEach
    fun init() {
        val initMember = Member("init","init","init", null,1L)
        memberRepository.save(initMember)
    }

    @AfterEach
    fun clear() {
        memberRepository.deleteAll()
    }


    @Test
    fun joinTest() {
        val member = Member("test","test","test", null,2L)
        memberService.join(member)
        val findMember = memberRepository.findById(member.id).get()
        Assertions.assertThat(member.loginId).isEqualTo(findMember.loginId)
    }

    @Test
    fun joinExceptionTest() {
        val initMember = Member("init","init","init", null,1L)
        val assertThrows = assertThrows(IllegalArgumentException::class.java) {
            memberService.join(initMember)
        }
        assertThat(assertThrows.message).isEqualTo("이미 존재하는 회원입니다.")
    }

    @Test
    fun loginTest() {
        val member = memberService.login("init","init")
        val findMember = memberRepository.findByLoginId("init")
        Assertions.assertThat(member).isEqualTo(findMember)
    }


    @Test
    fun loginExceptionTest() {
        val assertThrow = assertThrows(IllegalArgumentException::class.java) {
            memberService.login("init","test")
        }
        assertThat(assertThrow.message).isEqualTo("아이디 또는 패스워드가 틀렸습니다.")
    }

    @Test
    fun updateTest() {

        //given
        val member = Member("test","test","test", null,1L)
        memberRepository.save(member)
        //when
        val form = MemberUpdateForm("init","init")
        memberService.update(member.loginId,form)
        val updateMember = memberRepository.findByLoginId(member.loginId)
        //then
        assertThat(member.loginId).isEqualTo(updateMember?.loginId)
        assertThat(updateMember?.password).isEqualTo("init")
        assertThat(updateMember?.name).isEqualTo("init")

    }

    @Test
    fun updateExceptionTest() {
        val member = Member("test","test","test", null,1L)
        memberRepository.save(member)
        //when
        val form = MemberUpdateForm("init","init")
        val assertThrow = assertThrows(MemberNotFoundException::class.java) {
            memberService.update("error",form)
        }

        assertThat(assertThrow.message).isEqualTo("회원을 찾을 수 없습니다.")

    }

    @Test
    fun deleteTest() {
        val member = Member("test","test","test", null,1L)
        memberRepository.save(member)
        //when
        memberService.delete(member.loginId)
        Assertions.assertThat(memberRepository.findByLoginId(member.loginId)).isNull()
    }

    @Test
    fun deleteExceptionTest() {
        val member = Member("test","test","test", null,1L)
        memberRepository.save(member)
        //when
        val assertThrow = assertThrows(MemberNotFoundException::class.java) {
            memberService.delete("error")
        }

        assertThat(assertThrow.message).isEqualTo("회원을 찾을 수 없습니다.")

    }

    @Test
    fun findAll() {
        val member = Member("test","test","test", null,1L)
        memberRepository.save(member)
        val members : List<Member>? = memberService.findAll()
        Assertions.assertThat(members?.size).isEqualTo(2)
    }


}