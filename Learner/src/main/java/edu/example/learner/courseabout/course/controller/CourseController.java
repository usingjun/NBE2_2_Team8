package edu.example.learner.courseabout.course.controller;

import edu.example.learner.courseabout.course.dto.CourseDTO;
import edu.example.learner.courseabout.course.service.CourseService;

import edu.example.learner.courseabout.exception.CourseTaskException;
import edu.example.learner.courseabout.video.dto.VideoDTO;
import edu.example.learner.courseabout.video.entity.Video;
import edu.example.learner.courseabout.video.service.VideoService;
import edu.example.learner.member.dto.MemberDTO;
import edu.example.learner.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    private final MemberService memberService;

    private final VideoService videoService;

    @PostMapping()
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {

        log.info("Creating course {}", courseDTO);

        return ResponseEntity.ok(courseService.addCourse(courseDTO));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> readCourse(@PathVariable Long courseId) {
        log.info("Reading course {}", courseId);
        CourseDTO courseDTO = courseService.read(courseId);
        return ResponseEntity.ok(courseDTO);
    }

    @GetMapping("/list/{memberId}")
    public ResponseEntity<List<CourseDTO>> readCourses(@PathVariable Long memberId) {
        log.info("Reading course {}", memberId);
        MemberDTO memberInfo = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(courseService.getCoursesByNickname(memberInfo.getNickname()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CourseDTO>> readCourseList() {
        log.info("Reading course list");
        return ResponseEntity.ok(courseService.readAll());
    }
    @GetMapping("/video/{courseId}")
    public ResponseEntity<List<VideoDTO>> getVideosByCourseId(@PathVariable Long courseId) {
        List<VideoDTO> videos = videoService.getVideosByCourseId(courseId);
        return ResponseEntity.ok(videos);
    }

    @PutMapping("")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO) {
        log.info("Updating course {}", courseDTO);
        return ResponseEntity.ok(courseService.updateCourse(courseDTO));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@RequestParam Long courseId) {
        log.info("Deleting course {}", courseId);
        return ResponseEntity.ok(Map.of("delete","success"));
    }

    @GetMapping("{memberId}/list")
    public ResponseEntity<List<CourseDTO>> readCourseListByMemberId(@PathVariable Long memberId) {
        log.info("Reading course list");
        return ResponseEntity.ok(courseService.readAll());
    }
}
