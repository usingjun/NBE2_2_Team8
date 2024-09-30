package edu.example.learner.courseabout.course.service;

import edu.example.learner.courseabout.course.dto.CourseDTO;
import edu.example.learner.courseabout.course.entity.Course;
import edu.example.learner.courseabout.course.entity.CourseAttribute;
import edu.example.learner.courseabout.course.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public CourseDTO addCourse(CourseDTO courseDTO) {
        Course course = courseRepository.save(courseDTO.toEntity());
        if (course != null) {
            return courseDTO;
        }else {
            return null;
        }
    }

    @Override
    public CourseDTO read(Long courseId) {
        Optional<Course> courseRepositoryById = courseRepository.findById(courseId);
        if (courseRepositoryById.isPresent()) {
            return new CourseDTO(courseRepositoryById.get());
        }
        return null;
    }

    @Override
    public List<CourseDTO> readByAttribute(CourseAttribute courseAttribute) {
        List<Course> courseList = courseRepository.readByCourseAttribute(courseAttribute);
        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (Course course : courseList) {
            courseDTOList.add(new CourseDTO(course));
        }
        return courseDTOList;
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Optional<Course> courseRepositoryById = courseRepository.findById(courseDTO.getCourseId());
        courseRepositoryById.ifPresent(course -> {
            course.changeCourseName(courseDTO.getCourseName());
            course.changeCourseLevel(courseDTO.getCourseLevel());
            course.changeCourseDescription(courseDTO.getCourseDescription());
            course.changeCourseLevel(courseDTO.getCourseLevel());
            course.changeCourseStatus(CourseAttribute.valueOf(courseDTO.getCourseAttribute()));
            course.changeInstructorName(courseDTO.getInstructorName());
            course.changeSale(courseDTO.isSale());
        });
        return courseDTO;
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(2L);
    }

    @Override
    public List<CourseDTO> readAll() {
        List<Course> courseList = courseRepository.findAll();

        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (Course course : courseList) {
            courseDTOList.add(new CourseDTO(course));
        }

        return courseDTOList;
    }
}
