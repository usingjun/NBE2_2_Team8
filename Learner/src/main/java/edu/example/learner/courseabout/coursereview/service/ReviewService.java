package edu.example.learner.courseabout.coursereview.service;

import edu.example.learner.courseabout.coursereview.dto.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO getReviewById(Long reviewId);
    ReviewDTO updateReview(ReviewDTO reviewDTO);
    void deleteReview(Long reviewId);

    List<ReviewDTO> getCourseReviewList(Long courseId, ReviewDTO reviewDTO);
    List<ReviewDTO> getInstructorReviewList(Long memberId, ReviewDTO reviewDTO);
}

