package edu.example.learner.course.repository;

import edu.example.learner.course.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
