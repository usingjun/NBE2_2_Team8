package edu.example.learner.course.controller;

import edu.example.learner.course.dto.CourseInquiryDTO;
import edu.example.learner.course.entity.CourseInquiry;
import edu.example.learner.course.service.CourseInquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/course_inquiry")
public class CourseInquiryController {
    private final CourseInquiryService courseInquiryService;

    //문의 등록
    @PostMapping("/create")
    public ResponseEntity<CourseInquiryDTO> register(@RequestBody CourseInquiryDTO courseInquiryDTO){
        return ResponseEntity.ok(courseInquiryService.register(courseInquiryDTO));
    }

    //전체 문의 조회
    @GetMapping
    public ResponseEntity<List<CourseInquiryDTO>> getList(){
        List<CourseInquiry> courseInquiries = courseInquiryService.readAll();
        List<CourseInquiryDTO> courseInquiryDTOS = courseInquiries
                .stream()
                .map(CourseInquiryDTO::new)
                .toList();

        return ResponseEntity.ok(courseInquiryDTOS);
    }

    //문의 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity<CourseInquiryDTO> getInquiry( @PathVariable("inquiryId") Long inquiryId ){
        return ResponseEntity.ok(courseInquiryService.read(inquiryId));
    }

    //문의 수정
    @PutMapping("/{inquiryId}/update")
    public ResponseEntity<CourseInquiryDTO> modify( @PathVariable("inquiryId") Long inquiryId ,
                                                    @RequestBody CourseInquiryDTO courseInquiryDTO){
        courseInquiryDTO.setInquiryId(inquiryId);
        return ResponseEntity.ok(courseInquiryService.update(courseInquiryDTO));
    }

    //문의 삭제
    @DeleteMapping("/{inquiryId}/delete")
    public ResponseEntity<Void> delete( @PathVariable("inquiryId") Long inquiryId ){
        courseInquiryService.delete(inquiryId);
        return ResponseEntity.ok().build();
    }
}
