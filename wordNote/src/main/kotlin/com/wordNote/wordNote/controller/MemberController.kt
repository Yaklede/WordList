package com.wordNote.wordNote.controller

import com.wordNote.wordNote.domain.Member
import com.wordNote.wordNote.dto.member.MemberCreateForm
import com.wordNote.wordNote.dto.member.MemberUpdateForm
import com.wordNote.wordNote.service.MemberService
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

/**
 * Controller Advice를 이용하여
 * Exception 에러 공통처리 할 것
 */
@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService,
) {

    @PostConstruct
    fun initMember() {
        val form = MemberCreateForm("testId","pass","name")
        memberService.join(form)
    }

    @GetMapping("/list")
    fun memberList() : List<Member> {
        return memberService.findAll()
    }

    @PostMapping("/join")
    fun join(@ModelAttribute("memberCreateForm") form : MemberCreateForm) {
        memberService.join(form)
    }

    @PatchMapping("/update/{loginId}")
    fun update(@PathVariable("loginId") loginId : String ,@ModelAttribute("memberUpdateForm") form : MemberUpdateForm){
        memberService.update(loginId,form)
    }

    @DeleteMapping("/delete/{loginId}")
    fun delete(@PathVariable("loginId") loginId : String){
        memberService.delete(loginId)
    }
}