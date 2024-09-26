package edu.example.learner.course.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "news")
public class NewsEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long newsId;

    private String newsName;

    private String newsDescription;

    @CreatedDate
    private LocalDateTime newsDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course courseNews;

    public void changeNewsName(String newsName) {
        this.newsName = newsName;
    }

    public void changeNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public void changeCourse(Course course) {
        this.courseNews = course;
        course.getNewsEntities().add(this);
    }

    public NewsEntity(String newsName, String newsDescription) {
        this.newsName = newsName;
        this.newsDescription = newsDescription;
    }
}
