package edu.example.learner.courseabout.coursereview.service;

import edu.example.learner.courseabout.coursereview.dto.ReviewDTO;
import edu.example.learner.courseabout.coursereview.entity.Review;
import edu.example.learner.courseabout.exception.ReviewException;
import edu.example.learner.courseabout.coursereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        try {
            Review review = reviewDTO.toEntity();
            System.out.println(review);
            reviewRepository.save(review);
            return new ReviewDTO(review);
        } catch (Exception e) {
            log.error("--- e : " + e);
            log.error("--- " + e.getMessage()); //에러 로그로 발생 예외의 메시지를 기록하고
            throw ReviewException.NOT_REGISTERED.get();
        }
    }

    @Override
    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewException.NOT_FOUND::get);
        return new ReviewDTO(review);
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewDTO.toEntity().getReviewId()).orElseThrow(ReviewException.NOT_FOUND::get);

        Review newReview = reviewDTO.toEntity();
        try {
            review.changeReviewName(newReview.getReviewName());
            review.changeReviewDetail(newReview.getReviewDetail());
            review.changeRating(newReview.getRating());
            return new ReviewDTO(review);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ReviewException.NOT_MODIFIED.get();
        }
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewException.NOT_FOUND::get);

        try {
            reviewRepository.delete(review);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ReviewException.NOT_REMOVED.get();
        }
    }

    @Override
    public List<ReviewDTO> getCourseReviewList(Long courseId, ReviewDTO reviewDTO) {
        System.out.println("service");
        List<Review> reveiewList = reviewRepository.getCourseReview(courseId).orElse(null);

        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        if (reveiewList.isEmpty()) {
            return reviewDTOList;
        }

        reveiewList.forEach(review -> {
            reviewDTOList.add(ReviewDTO.builder()
                    .reviewId(review.getReviewId())
                    .reviewName(review.getReviewName())
                    .reviewDetail(review.getReviewDetail())
                    .rating(review.getRating())
                    .reviewType(review.getReviewType())
                    .writerId(review.getMember().getMemberId())
                    .courseId(review.getCourse().getCourseId())
                    .build());

        });

        return reviewDTOList;
    }

    @Override
    public List<ReviewDTO> getInstructorReviewList(String instructorName, ReviewDTO reviewDTO) {
        System.out.println("service");
        List<Review> reveiewList = reviewRepository.getInstructorReview(instructorName).orElse(null);

        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        if (reveiewList.isEmpty()) {
            return reviewDTOList;
        }

        reveiewList.forEach(review -> {
            reviewDTOList.add(ReviewDTO.builder()
                    .reviewId(review.getReviewId())
                    .reviewName(review.getReviewName())
                    .reviewDetail(review.getReviewDetail())
                    .rating(review.getRating())
                    .reviewType(review.getReviewType())
                    .writerId(review.getMember().getMemberId())
                    .courseId(review.getCourse().getCourseId())
                    .build());

        });

        return reviewDTOList;
    }

}