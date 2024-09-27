package edu.example.learner.repository.search;

import edu.example.learner.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberSearch {
    Member getMemberInfo(Long id);

    List<Member> getAllMembers();

    Optional<Member> getMemberByEmail(String email);
}
