package edu.example.learner.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="course_answer")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CourseAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    private String answerContent;

    @CreatedDate
    private LocalDateTime answerCreateDate;

    @ManyToOne
    @JoinColumn(name = "inquiry_id")
    private CourseInquiry courseInquiry;

    public void changeAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
}
