package edu.example.learner.course.entity;

import edu.example.learner.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue
    private Long reviewId;

    private String reviewName;

    private String reviewDetail;

    private int rating;

    @CreatedDate
    private LocalDateTime reviewCreatedDate;

    @LastModifiedDate
    private LocalDateTime reviewUpdatedDate;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void changeReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public void changeReviewDetail(String reviewDetail) {
        this.reviewDetail = reviewDetail;
    }

    public void changeRating(int rating) {
        this.rating = rating;
    }
}
