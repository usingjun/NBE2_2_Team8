package edu.example.learner.repository;

import edu.example.learner.entity.Member;
import edu.example.learner.repository.search.MemberSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer>, MemberSearch {
    @Override
    Member getMemberInfo(Long id);

    @Override
    List<Member> getAllMembers();
}
