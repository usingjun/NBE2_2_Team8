package edu.example.learner.course.dto;

import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.Review;
import edu.example.learner.course.entity.ReviewType;
import edu.example.learner.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseReviewDTO implements ReviewDTO{
    private Long reviewId;

    private String reviewName;

    private String reviewDetail;

    private int rating;

    private LocalDateTime reviewCreatedDate;

    private LocalDateTime reviewUpdatedDate;

    private ReviewType reviewType;

    private Long memberId;

    private String CourseName;
    private String CourseBio;

    public CourseReviewDTO(Review review, Course course) {
        this.reviewId = review.getReviewId();
        this.reviewName = review.getReviewName();
        this.reviewDetail = review.getReviewDetail();
        this.rating = review.getRating();
        this.reviewCreatedDate = review.getReviewCreatedDate();
        this.reviewUpdatedDate = review.getReviewUpdatedDate();
        this.reviewType = review.getReviewType();
        this.memberId = review.getMember().getMemberId();
        this.CourseName = course.getCourseName();
        this.CourseBio = course.getCourseDescription();
    }

    public Review toEntity() {
        Member member = Member.builder().memberId(memberId).build();

        return Review.builder()
                .reviewId(reviewId)
                .reviewName(reviewName)
                .reviewDetail(reviewDetail)
                .rating(rating)
                .reviewCreatedDate(reviewCreatedDate)
                .reviewUpdatedDate(reviewUpdatedDate)
                .reviewType(reviewType)
                .member(member)
                .build();
    }
}
