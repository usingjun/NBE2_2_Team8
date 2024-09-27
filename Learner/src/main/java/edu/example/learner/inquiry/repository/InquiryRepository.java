package edu.example.learner.inquiry.repository;

import edu.example.learner.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
