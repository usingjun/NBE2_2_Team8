package edu.example.learner.courseabout.course.repository;

import edu.example.learner.courseabout.course.entity.Course;
import edu.example.learner.courseabout.course.entity.MemberCourse;
import edu.example.learner.courseabout.course.repository.search.MemberCourseSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCourseRepository extends JpaRepository<MemberCourse, Long> , MemberCourseSearch {
}
