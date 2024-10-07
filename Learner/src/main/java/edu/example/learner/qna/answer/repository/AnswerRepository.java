package edu.example.learner.qna.answer.repository;

import edu.example.learner.qna.answer.entity.Answer;
import edu.example.learner.qna.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("select a from Answer a where a.inquiry.inquiryId = :inquiryId")
    Optional<Answer> findByInquiryId(Long inquiryId);
}
