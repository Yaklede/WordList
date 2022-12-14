package com.wordNote.wordNote.service

import com.wordNote.wordNote.domain.Member
import com.wordNote.wordNote.dto.member.MemberCreateForm
import com.wordNote.wordNote.dto.member.MemberUpdateForm
import com.wordNote.wordNote.exception.MemberNotFoundException
import com.wordNote.wordNote.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalArgumentException
@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun join(memberForm : MemberCreateForm) : Long? {
        val member = Member(memberForm.loginId,memberForm.password,memberForm.name)
        validDuplicateLoginId(member)
        memberRepository.save(member)
        return member.id
    }

    private fun validDuplicateLoginId(member: Member) {
        val findMember = memberRepository.findByLoginId(member.loginId)
        if (findMember != null) {
            return throw IllegalArgumentException("이미 존재하는 회원입니다.")
        }
    }

    fun login(loginId : String, password : String) : Member? {
        val findMember = memberRepository.findOptionalByLoginId(loginId)
        return findMember.filter { findMember -> findMember.password == password }
            .orElse(null) ?: throw IllegalArgumentException("아이디 또는 패스워드가 틀렸습니다.")
    }
    //form data 는 Controller Layer에서 만들어서 넘겨줘야 함
    @Transactional
    fun update(loginId : String ,form : MemberUpdateForm) {
        val findMember = getMemberByLoginId(loginId)
        findMember.update(form)
    }

    @Transactional
    fun delete(loginId : String) {
        val findMember = getMemberByLoginId(loginId)
        memberRepository.delete(findMember)
    }
    fun findByLoginId(loginId: String) : Member? {
        return getMemberByLoginId(loginId)
    }
    private fun getMemberByLoginId(loginId: String): Member {
        return memberRepository.findByLoginId(loginId) ?: return throw MemberNotFoundException()
    }
    fun findById(memberId : Long?) : Member? {
        return memberRepository.findById(memberId).orElse(null) ?: return throw MemberNotFoundException()
    }

    fun findAll() : List<Member> {
        return memberRepository.findAll()
    }
}