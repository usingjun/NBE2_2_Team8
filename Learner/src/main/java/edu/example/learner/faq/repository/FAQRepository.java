package edu.example.learner.faq.repository;

import edu.example.learner.faq.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQ> findByFaqCategory(String faqCategory);
}
