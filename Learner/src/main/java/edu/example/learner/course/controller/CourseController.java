package edu.example.learner.course.controller;

import edu.example.learner.course.dto.CourseDTO;
import edu.example.learner.course.service.CourseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {

        log.info("Creating course {}", courseDTO);

        return ResponseEntity.ok(courseService.addCourse(courseDTO));
    }
    @GetMapping
    public ResponseEntity<CourseDTO> readCourse(@RequestParam Long courseId) {

        log.info("Reading course {}", courseId);

        return ResponseEntity.ok(courseService.read(courseId));
    }

    @PutMapping("/update")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO) {
        log.info("Updating course {}", courseDTO);
        return ResponseEntity.ok(courseService.updateCourse(courseDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCourse(@RequestParam Long courseId) {
        log.info("Deleting course {}", courseId);
        return ResponseEntity.ok(Map.of("delete","success"));
    }
}
