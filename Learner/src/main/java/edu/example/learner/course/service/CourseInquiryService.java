package edu.example.learner.course.service;

import edu.example.learner.course.dto.CourseInquiryDTO;
import edu.example.learner.course.entity.CourseInquiry;
import edu.example.learner.course.repository.CourseInquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CourseInquiryService {
    private final CourseInquiryRepository courseInquiryRepository;

    //문의 등록
    public CourseInquiryDTO register(CourseInquiryDTO courseInquiryDTO){

    }


    //전체 문의 조회 - readAll() : List<CourseInquiryDTO>

    //문의 수정 - update(CourseInquiryDTO) : CourseInquiryDTO
    //문의 삭제 - delete(Long) : void
}
