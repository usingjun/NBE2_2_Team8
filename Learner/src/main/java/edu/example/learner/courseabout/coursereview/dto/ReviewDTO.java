package edu.example.learner.courseabout.coursereview.dto;

import edu.example.learner.courseabout.course.entity.Course;
import edu.example.learner.courseabout.coursereview.entity.Review;
import edu.example.learner.courseabout.coursereview.entity.ReviewType;
import edu.example.learner.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long reviewId;
    private String reviewName;
    private String reviewDetail;
    private int rating;
    private LocalDateTime reviewCreatedDate;
    private LocalDateTime reviewUpdatedDate;
    private ReviewType reviewType;
    private Long writerId;
    private Long courseId;

    public ReviewDTO(Review review) {
        this.reviewId = review.getReviewId();
        this.reviewName = review.getReviewName();
        this.reviewDetail = review.getReviewDetail();
        this.rating = review.getRating();
        this.reviewCreatedDate = review.getReviewCreatedDate();
        this.reviewUpdatedDate = review.getReviewUpdatedDate();
        this.reviewType = review.getReviewType();
        this.writerId = review.getMember().getMemberId();
        this.courseId = review.getCourse().getCourseId();
    }

    public Review toEntity() {
        Member member = Member.builder().memberId(writerId).build();
        Course course = Course.builder().courseId(courseId).build();

        return Review.builder()
                .reviewId(reviewId)
                .reviewName(reviewName)
                .reviewDetail(reviewDetail)
                .rating(rating)
                .reviewCreatedDate(reviewCreatedDate)
                .reviewUpdatedDate(reviewUpdatedDate)
                .reviewType(reviewType)
                .member(member)
                .course(course)
                .build();
    }
}
