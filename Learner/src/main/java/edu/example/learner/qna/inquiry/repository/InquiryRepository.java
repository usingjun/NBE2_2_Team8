package edu.example.learner.qna.inquiry.repository;

import edu.example.learner.qna.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
