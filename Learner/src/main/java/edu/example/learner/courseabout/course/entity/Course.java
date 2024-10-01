package edu.example.learner.courseabout.course.entity;


import edu.example.learner.courseabout.news.entity.NewsEntity;
import edu.example.learner.courseabout.video.entity.Video;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = {"videos","newsEntities"})
@Table(name = "course")
@EntityListeners(AuditingEntityListener.class)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @NotEmpty
    private String courseName;

    @NotEmpty
    private String courseDescription;

    @Enumerated(EnumType.STRING)
    private CourseAttribute courseAttribute;

    @NotEmpty
    private String instructorName;

    @NotNull
    private Long coursePrice;
    @NotNull
    private Integer courseLevel;

    private boolean sale;

    public void changeCourseStatus(CourseAttribute courseStatus) {
        this.courseAttribute = courseStatus;
    }

    public void changeCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void changeCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public void changeInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void changePrice(Long coursePrice) {
        this.coursePrice = coursePrice;
    }

    public void changeCourseLevel(Integer courseLevel) {
        this.courseLevel = courseLevel;
    }

    public void changeSale(boolean sale) {
        this.sale = sale;
    }

    public void changeCourseCreatedDate(LocalDateTime courseCreatedDate) {
        this.courseCreatedDate = courseCreatedDate;
    }

    public void changeCourseModifiedDate(LocalDateTime courseModifiedDate) {
        this.courseModifiedDate = courseModifiedDate;
    }

    @CreatedDate
    private LocalDateTime courseCreatedDate;
    @LastModifiedDate
    private LocalDateTime courseModifiedDate;

    @OneToMany(mappedBy = "courseNews")
    @Builder.Default
    private List<NewsEntity> newsEntities = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Video> videos = new ArrayList<>();


}
