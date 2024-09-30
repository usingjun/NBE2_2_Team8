package edu.example.learner.courseabout.video.entity;

import edu.example.learner.courseabout.course.entity.Course;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long video_Id;

    private String title;

    private String url;

    private String description;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_Id")
    private Course course;

    // Getters and changeters

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeUrl(String url) {
        this.url = url;
    }

    public void changeDescription(String description) {
        this.description = description;
    }
}
