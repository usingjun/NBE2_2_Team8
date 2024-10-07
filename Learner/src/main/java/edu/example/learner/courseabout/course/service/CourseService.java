package edu.example.learner.courseabout.course.service;

import edu.example.learner.courseabout.course.dto.CourseDTO;
import edu.example.learner.courseabout.course.entity.CourseAttribute;
import edu.example.learner.courseabout.course.entity.MemberCourse;

import java.util.List;

public interface CourseService {
    CourseDTO addCourse(CourseDTO courseDTO);
    CourseDTO read(Long courseId);
    List<CourseDTO>readByAttribute(CourseAttribute courseAttribute);
    CourseDTO updateCourse(CourseDTO courseDTO);
    void deleteCourse(Long courseId);
    List<CourseDTO> readAll();
}
