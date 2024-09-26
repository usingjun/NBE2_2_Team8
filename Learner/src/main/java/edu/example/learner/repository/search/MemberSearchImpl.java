package edu.example.learner.repository.search;

import com.querydsl.jpa.impl.JPAQueryFactory;
import edu.example.learner.entity.Member;
import edu.example.learner.entity.QMember;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemberSearchImpl implements MemberSearch{
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember qMember = QMember.member;


    @Override
    public Member getMemberInfo(Long id) {
        return jpaQueryFactory
                .selectFrom(qMember)
                .where(qMember.memberId.eq(id))
                .fetchOne();
    }

    @Override
    public List<Member> getAllMembers() {
        return jpaQueryFactory
                .selectFrom(qMember)
                .fetch();
    }
}
