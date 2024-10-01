package edu.example.learner.courseabout.course.dto;

import edu.example.learner.courseabout.course.entity.Course;
import edu.example.learner.courseabout.course.entity.CourseAttribute;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CourseDTO {
    private Long courseId;
    private String courseName;
    private String courseDescription;
    private Long coursePrice;

    @Min(1)
    @Max(5)
    private Integer courseLevel;

    private String courseAttribute;
    private boolean sale;
    private String instructorName;

    public Course toEntity() {
        return Course.builder().courseId(courseId)
                //수정 필요
//                .courseName(courseName)
                .courseDescription(courseDescription)
                .courseName(courseName)
                .coursePrice(coursePrice)
                .courseLevel(courseLevel)
                .courseAttribute(CourseAttribute.valueOf(courseAttribute))
                .sale(sale)
                .build();
    }

    public CourseDTO(Course course) {
        this.courseId = course.getCourseId();
        this.courseName = course.getCourseName();
        this.courseDescription = course.getCourseDescription();
        this.coursePrice = course.getCoursePrice();
        this.courseLevel = course.getCourseLevel();
        this.courseAttribute = course.getCourseAttribute().name();
        this.sale = course.isSale();
        this.instructorName = course.getInstructorName().getNickname();
    }
}
