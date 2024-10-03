package edu.example.learner.courseabout.coursereview.controller;

import edu.example.learner.courseabout.coursereview.dto.ReviewDTO;
import edu.example.learner.courseabout.coursereview.service.ReviewServiceImpl;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course/{courseId}/reviews")
@Log4j2
public class CourseReviewController {

    private final ReviewServiceImpl reviewService;

    @PostMapping("/create")
    @Operation(summary = "Course Review 생성")
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO reviewDTO) {

        log.info("Create review: " + reviewDTO);

        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }


    @PutMapping("/update")
    @Operation(summary = "Course Review 수정")
    public ResponseEntity<ReviewDTO> update(@RequestBody ReviewDTO reviewDTO) {

        log.info("update Review: " + reviewDTO);
        return ResponseEntity.ok(reviewService.updateReview(reviewDTO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Course Review 삭제")
    public ResponseEntity<Map<String, String>> remove(@RequestParam Long reviewId) {

        log.info("Delete Review: " + reviewId);

        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @GetMapping("/list")
    @Operation(summary = "Course Review list 조회")
    public ResponseEntity<List<ReviewDTO>> reviewList(@PathVariable("courseId") Long courseId,
                                      ReviewDTO reviewDTO) {

        return ResponseEntity.ok(reviewService.getCourseReviewList(courseId, reviewDTO));
    }
}
