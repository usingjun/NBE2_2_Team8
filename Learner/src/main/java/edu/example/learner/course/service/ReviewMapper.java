package edu.example.learner.course.service;

import edu.example.learner.course.dto.CourseReviewDTO;
import edu.example.learner.course.dto.InstructorReviewDTO;
import edu.example.learner.course.dto.ReviewDTO;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.Review;
import edu.example.learner.course.entity.ReviewType;
import org.springframework.stereotype.Service;

@Service
public class ReviewMapper {
    public ReviewDTO toDTO(Review review) {
        if (review.getReviewType() == ReviewType.INSTRUCTOR) {

            return new InstructorReviewDTO(
                    review.getReviewId(),
                    review.getReviewName(),
                    review.getReviewDetail(),
                    review.getRating(),
                    review.getReviewCreatedDate(),
                    review.getReviewUpdatedDate(),
                    review.getReviewType(),
                    review.getMember().getMemberId(),
                    "Instructor Name",
                    "Instructor Bio"
            );
        } else if (review.getReviewType() == ReviewType.COURSE) {
            return new CourseReviewDTO(
                    review.getReviewId(),
                    review.getReviewName(),
                    review.getReviewDetail(),
                    review.getRating(),
                    review.getReviewCreatedDate(),
                    review.getReviewUpdatedDate(),
                    review.getReviewType(),
                    review.getMember().getMemberId(),
                    review.getCourse().getCourseName(),
                    review.getCourse().getCourseDescription()
            );
        } else {
            throw new IllegalArgumentException("Unknown ReviewType: " + review.getReviewType());
        }
    }
}
