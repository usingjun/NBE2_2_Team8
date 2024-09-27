package edu.example.learner.course.repository;

import edu.example.learner.course.dto.CourseInquiryDTO;
import edu.example.learner.course.entity.CourseInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseInquiryRepository extends JpaRepository<CourseInquiry, Long> {
}
