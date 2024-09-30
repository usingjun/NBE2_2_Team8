package edu.example.learner.course.repository;

import edu.example.learner.course.entity.CourseInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseInquiryRepository extends JpaRepository<CourseInquiry, Long> {
}
