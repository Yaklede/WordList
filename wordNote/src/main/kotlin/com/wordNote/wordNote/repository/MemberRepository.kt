package com.wordNote.wordNote.repository

import com.wordNote.wordNote.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : JpaRepository<Member,Long> {
    fun findByName(name : String) : Member?
    fun findByLoginId(loginId : String) : Member?
    fun findOptionalByLoginId(loginId : String) : Optional<Member>
}