package edu.example.learner.courseabout.course.controller;

import edu.example.learner.courseabout.course.dto.CourseDTO;
import edu.example.learner.courseabout.course.service.CourseService;
import edu.example.learner.courseabout.video.dto.VideoDTO;
import edu.example.learner.courseabout.video.service.VideoService;
import edu.example.learner.member.dto.MemberDTO;
import edu.example.learner.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "강의 컨트롤러", description = "강의 CRUD를 담당하는 컨트롤러입니다.")
public class CourseController {
    private final CourseService courseService;
    private final MemberService memberService;
    private final VideoService videoService;

    @PostMapping()
    @Operation(summary = "강의 생성", description = "새로운 강의를 등록합니다.")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        log.info("강의 생성 {}", courseDTO);
        return ResponseEntity.ok(courseService.addCourse(courseDTO));
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "강의 조회", description = "강의 ID로 강의 세부 정보를 조회합니다.")
    public ResponseEntity<CourseDTO> readCourse(@Parameter(description = "조회할 강의 ID") @PathVariable Long courseId) {
        log.info("강의 조회 {}", courseId);
        CourseDTO courseDTO = courseService.read(courseId);
        return ResponseEntity.ok(courseDTO);
    }

    @GetMapping("/list/{memberId}")
    @Operation(summary = "회원의 강의 조회", description = "회원이 생성한 강의 목록을 조회합니다.")
    public ResponseEntity<List<CourseDTO>> readCourses(@Parameter(description = "회원 ID") @PathVariable Long memberId) {
        log.info("회원 {}의 강의 조회", memberId);
        MemberDTO memberInfo = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(courseService.getCoursesByNickname(memberInfo.getNickname()));
    }

    @GetMapping("/list")
    @Operation(summary = "모든 강의 조회", description = "모든 강의 목록을 조회합니다.")
    public ResponseEntity<List<CourseDTO>> readCourseList() {
        log.info("강의 목록 조회");
        return ResponseEntity.ok(courseService.readAll());
    }

    @GetMapping("/video/{courseId}")
    @Operation(summary = "강의 ID로 비디오 조회", description = "특정 강의에 연결된 비디오 목록을 조회합니다.")
    public ResponseEntity<List<VideoDTO>> getVideosByCourseId(@Parameter(description = "강의 ID") @PathVariable Long courseId) {
        List<VideoDTO> videos = videoService.getVideosByCourseId(courseId);
        return ResponseEntity.ok(videos);
    }

    @PutMapping("")
    @Operation(summary = "강의 수정", description = "기존 강의의 세부 정보를 수정합니다.")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO) {
        log.info("강의 수정 {}", courseDTO);
        return ResponseEntity.ok(courseService.updateCourse(courseDTO));
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "강의 삭제", description = "강의 ID로 강의를 삭제합니다.")
    public ResponseEntity<?> deleteCourse(@Parameter(description = "삭제할 강의 ID") @PathVariable Long courseId) {
        log.info("강의 삭제 {}", courseId);
        courseService.deleteCourse(courseId); // 실제 삭제 로직 추가 필요
        return ResponseEntity.ok(Map.of("delete", "성공"));
    }

    @GetMapping("{memberId}/list")
    @Operation(summary = "회원 ID로 강의 목록 조회", description = "회원 ID로 강의 목록을 조회합니다.")
    public ResponseEntity<List<CourseDTO>> readCourseListByMemberId(@Parameter(description = "회원 ID") @PathVariable Long memberId) {
        log.info("회원 {}의 강의 목록 조회", memberId);
        return ResponseEntity.ok(courseService.readAll());
    }
}
