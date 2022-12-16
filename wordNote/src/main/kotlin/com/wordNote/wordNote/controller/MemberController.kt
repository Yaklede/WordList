package com.wordNote.wordNote.controller

import com.wordNote.wordNote.domain.Member
import com.wordNote.wordNote.dto.member.LoginForm
import com.wordNote.wordNote.dto.member.MemberCreateForm
import com.wordNote.wordNote.dto.member.MemberDTO
import com.wordNote.wordNote.dto.member.MemberUpdateForm
import com.wordNote.wordNote.service.MemberService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

/**
 * Controller Advice를 이용하여
 * Exception 에러 공통처리 할 것
 */
@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService,
) {
    @GetMapping("/list")
    fun memberList() : List<MemberDTO> {
        val list = memberService.findAll()
        return list.map { member: Member -> MemberDTO(member)}
    }

    @GetMapping("/{loginId}")
    fun memberFindOne(@PathVariable("loginId") loginId: String) : MemberDTO {
        val member = memberService.findByLoginId(loginId)
        return MemberDTO(member)
    }

    @PostMapping("/login")
    fun login(@ModelAttribute("loginForm") loginForm : LoginForm , request : HttpServletRequest) {
        val loginMember = memberService.login(loginForm.loginId,loginForm.password)
        val session = request.session
        session.setAttribute("loginMember",loginMember)
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest) {
        request.getSession(false)?.invalidate()
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