package edu.example.learner.course.controller.review;

import edu.example.learner.course.dto.ReviewDTO;
import edu.example.learner.course.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@Log4j2
public class InstructorReviewController {

    private final ReviewServiceImpl reviewService;

    @PostMapping("/create")
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO reviewDTO) {

        log.info("Create review: " + reviewDTO);

        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    @GetMapping
    public ResponseEntity<ReviewDTO> read(@RequestParam Long reviewId) {
        log.info("Read Review: " + reviewId);
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @PutMapping("/update")
    public ResponseEntity<ReviewDTO> update(@RequestBody ReviewDTO reviewDTO) {

        log.info("update Review: " + reviewDTO);
        return ResponseEntity.ok(reviewService.updateReview(reviewDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> remove(@RequestParam Long reviewId) {

        log.info("Delete Review: " + reviewId);

        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReviewDTO>> reviewList(@RequestParam String instructorName,
                                      ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.getInstructorReviewList(instructorName, reviewDTO));
    }
}
