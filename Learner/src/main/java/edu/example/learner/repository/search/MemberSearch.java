package edu.example.learner.repository.search;

import edu.example.learner.entity.Member;

import java.util.List;

public interface MemberSearch {
    Member getMemberInfo(Long id);

    List<Member> getAllMembers();
}
