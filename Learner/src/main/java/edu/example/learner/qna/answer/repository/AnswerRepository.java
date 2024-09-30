package edu.example.learner.qna.answer.repository;

import edu.example.learner.qna.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
