package edu.example.learner.course.service;

import edu.example.learner.course.dto.CourseDTO;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.CourseAttribute;

import java.util.List;

public interface CourseService {
    CourseDTO addCourse(CourseDTO courseDTO);
    CourseDTO read(Long courseId);
    List<CourseDTO>readByAttribute(CourseAttribute courseAttribute);
    CourseDTO updateCourse(CourseDTO courseDTO);
    void deleteCourse(Long courseId);
    List<CourseDTO> readAll();

}
