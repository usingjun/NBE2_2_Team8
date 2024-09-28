package edu.example.learner.course.service;


import edu.example.learner.course.entity.TemporaryMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryMemberRepository extends JpaRepository<TemporaryMember, Long> {

}
