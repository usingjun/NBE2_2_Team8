package edu.example.learner.course.controller;

import edu.example.learner.course.dto.CourseAnswerDTO;
import edu.example.learner.course.entity.CourseAnswer;
import edu.example.learner.course.service.CourseAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/course_answer")
public class CourseAnswerController {
    private final CourseAnswerService courseAnswerService;

    //강의 문의 답변 생성
    @PostMapping("/create")
    public ResponseEntity<CourseAnswerDTO> register(@RequestBody CourseAnswerDTO courseAnswerDTO){
        return ResponseEntity.ok(courseAnswerService.register(courseAnswerDTO));
    }

    //강의 문의 답변 전체 보기
    @GetMapping("/{inquiryId}")
    public ResponseEntity<List<CourseAnswer>> getList( @PathVariable("inquiryId") Long inquiryId){
        return ResponseEntity.ok(courseAnswerService.readAll(inquiryId));
    }

    //강의 문의 답변 수정
    @PutMapping("/{answerId}/update")
    public ResponseEntity<CourseAnswerDTO> modify( @PathVariable("answerId") Long answerId,
                                    @RequestBody CourseAnswerDTO courseAnswerDTO){

        courseAnswerDTO.setAnswerId(answerId);
        return ResponseEntity.ok(courseAnswerService.update(courseAnswerDTO));
    }

    //강의 문의 답변 삭제
    @DeleteMapping("/{answerId}/delete")
    public ResponseEntity<Void> remove( @PathVariable("answerId") Long answerId){
        courseAnswerService.delete(answerId);
        return ResponseEntity.ok().build();
    }
}
