package edu.example.learner.course.service;

import edu.example.learner.course.dto.ReviewDTO;
import edu.example.learner.course.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    public ReviewDTO getReviewDTO(Review review) {
        return reviewMapper.toDTO(review);
    }

    public void saveReview(ReviewDTO reviewDTO) {
        // ReviewDTO에 따라 저장 로직 처리
    }
}
