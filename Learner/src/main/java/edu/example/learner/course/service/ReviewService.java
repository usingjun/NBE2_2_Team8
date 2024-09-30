package edu.example.learner.course.service;

import edu.example.learner.course.dto.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO getReviewById(Long reviewId);
    ReviewDTO updateReview(ReviewDTO reviewDTO);
    void deleteReview(Long reviewId);

    List<ReviewDTO> getCourseReviewList(Long courseId, ReviewDTO reviewDTO);
    List<ReviewDTO> getInstructorReviewList(String instructorName, ReviewDTO reviewDTO);
}

