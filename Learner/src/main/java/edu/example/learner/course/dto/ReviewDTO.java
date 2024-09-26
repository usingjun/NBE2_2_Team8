package edu.example.learner.course.dto;

import edu.example.learner.course.entity.Review;
import edu.example.learner.course.entity.ReviewType;

import java.time.LocalDateTime;

public interface ReviewDTO {
    Review toEntity();
}
