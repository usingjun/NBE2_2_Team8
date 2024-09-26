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
    private LocalDateTime newDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "class_id")
//    private CourseEntity course;


}
