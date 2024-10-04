package edu.example.learner.courseabout.news.controller;

import edu.example.learner.courseabout.course.service.CourseServiceImpl;
import edu.example.learner.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "NewsUtil", description = "새소식 유틸 API")
public class NewsUtilController {
    private final CourseServiceImpl courseServiceImpl;
    private final MemberService memberService;

    public NewsUtilController(CourseServiceImpl courseServiceImpl, MemberService memberService) {
        this.courseServiceImpl = courseServiceImpl;
        this.memberService = memberService;
    }

    @GetMapping("/course/{courseId}/member-nickname")
    @Operation(summary = "코스의 강사 닉네임 조회", description = "코스의 강사 닉네임을 조회합니다.")
    public ResponseEntity<String> getCourseInstructorNickname(@PathVariable Long courseId) {
        String instructorNickname = courseServiceImpl.getInstructorNicknameByCourseId(courseId);

        if (instructorNickname != null) {
            return ResponseEntity.ok(instructorNickname);  // 닉네임을 성공적으로 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 코스 또는 닉네임을 찾을 수 없습니다.");
        }
    }

    @GetMapping("/member/nickname")
    @Operation(summary = "이메일로 닉네임 조회", description = "이메일로 닉네임을 조회합니다.")
    public ResponseEntity<String> getNickname(@RequestParam String email) {
        String nickname = memberService.getNicknameByEmail(email);

        if (nickname != null) {
            return ResponseEntity.ok(nickname);  // 닉네임을 성공적으로 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("닉네임을 찾을 수 없습니다.");
        }
    }


}
