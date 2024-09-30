package edu.example.learner.course.repository;

import edu.example.learner.course.dto.CourseDTO;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.CourseAttribute;
import edu.example.learner.course.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
@SpringBootTest
@RequiredArgsConstructor
@TestPropertySource(properties = {
        "org=Spring",
        "name=Boot"
})
public class CourseRepositoryTests {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void add(){
        Course course = courseRepository.save(Course.builder().courseAttribute(CourseAttribute.C)
                .courseDescription("testDB3")
                .courseName("test강의3")
                .courseLevel(2)
                .coursePrice(5000L)
                .sale(false)
                .courseAttribute(CourseAttribute.C)
                .instructorName("test강사3")
                .build());
        Assertions.assertThat(course).isNotNull();
    }

    @Test
    void readByCourseAttribute(){
        List<Course> courseList = courseRepository.readByCourseAttribute(CourseAttribute.JAVA);
        assertThat(courseList).isNotNull();
        assertThat(courseList.size()).isEqualTo(1);
    }
    @Test
    @Transactional
    @Commit
    void updateCourse(){
        CourseDTO courseDTO = CourseDTO.builder().courseId(2L).courseDescription("변경")
                .courseName("modName").courseAttribute(String.valueOf(CourseAttribute.C))
                .coursePrice(3000L)
                .sale(false)
                .courseLevel(3).build();
        Optional<Course> courseRepositoryById = courseRepository.findById(courseDTO.getCourseId());
        Course coursefound = courseRepositoryById.get();
        courseRepositoryById.ifPresent(course -> {
            course.changeCourseLevel(courseDTO.getCourseLevel());
            course.changeCourseDescription(courseDTO.getCourseDescription());
            course.changeCourseLevel(courseDTO.getCourseLevel());
            course.changeCourseStatus(CourseAttribute.valueOf(courseDTO.getCourseAttribute()));
            course.changeInstructorName(courseDTO.getInstructorName());
            course.changeSale(courseDTO.isSale());
        });
        assertEquals("변경", coursefound.getCourseDescription());
    }
    @Test
    void deleteCourse() {
        courseRepository.deleteById(2L);
        courseRepository.findById(2L).orElseThrow();
    }
    @Test
    void readAll(){
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).isNotNull();
    }

}
