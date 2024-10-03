package edu.example.learner.courseabout.coursereview.controller;

import edu.example.learner.courseabout.coursereview.dto.ReviewDTO;
import edu.example.learner.courseabout.coursereview.service.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/{memberId}/reviews")
@Log4j2
public class InstructorReviewController {

    private final ReviewServiceImpl reviewService;

    @PostMapping("/create")
    @Operation(summary = "Instructor Review 생성")
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO reviewDTO) {

        log.info("Create review: " + reviewDTO);

        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    @PutMapping("/update")
    @Operation(summary = "Instructor Review 수정")
    public ResponseEntity<ReviewDTO> update(@RequestBody ReviewDTO reviewDTO) {

        log.info("update Review: " + reviewDTO);
        return ResponseEntity.ok(reviewService.updateReview(reviewDTO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Instructor Review 삭제")
    public ResponseEntity<Map<String, String>> remove(@RequestParam Long reviewId) {

        log.info("Delete Review: " + reviewId);

        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @GetMapping("/list")
    @Operation(summary = "Instructor Review list 조회")
    public ResponseEntity<List<ReviewDTO>> reviewList(@PathVariable("memberId") Long memberId,
                                      ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.getInstructorReviewList(memberId, reviewDTO));
    }
}
