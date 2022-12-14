package com.wordNote.wordNote.repository.wordSet

import com.querydsl.jpa.impl.JPAQueryFactory
import com.wordNote.wordNote.domain.QWordSet.wordSet
import com.wordNote.wordNote.domain.QMember.member
import com.wordNote.wordNote.domain.WordSet

class WordSetCustomRepositoryImpl(
    private val queryFactory : JPAQueryFactory,
) : WordSetCustomRepository {
    override fun findByLoginId(loginId: String): List<WordSet>? {
        return queryFactory
            .select(wordSet)
            .from(wordSet)
            .leftJoin(wordSet.member,member).on(wordSet.member.loginId.eq(loginId))
            .fetch()
    }
}