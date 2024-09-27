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
    @Column(name = "news_id")
    private Long newsId;

    @Column(nullable = false)
    private String newsName;

    private String newsDescription;

    @Column(columnDefinition = "integer default 0", nullable = false)	// 조회수의 기본 값을 0으로 지정, null 불가 처리
    private int viewCount;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likeCount;

    @Column(columnDefinition = "bigint default 0", nullable = false)
    private Long CommentCnt;

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
}
