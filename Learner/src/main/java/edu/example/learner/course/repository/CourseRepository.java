package edu.example.learner.course.repository;

import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.CourseAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> readByCourseAttribute(CourseAttribute courseAttribute);
}
