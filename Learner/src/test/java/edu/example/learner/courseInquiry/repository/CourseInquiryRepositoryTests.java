package edu.example.learner.courseInquiry.repository;

import edu.example.learner.course.dto.CourseInquiryDTO;
import edu.example.learner.course.entity.CourseInquiry;
import edu.example.learner.course.entity.InquiryStatus;
import edu.example.learner.course.exception.CourseInquiryException;
import edu.example.learner.course.exception.CourseInquiryTaskException;
import edu.example.learner.course.repository.CourseInquiryRepository;
import edu.example.learner.course.service.CourseInquiryService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Log4j2
public class CourseInquiryRepositoryTests {
    @Autowired
    private CourseInquiryRepository courseInquiryRepository;
    @Autowired
    private CourseInquiryService courseInquiryService;

    private CourseInquiryDTO testCourseInquiryDTO;

    @Test
    @DisplayName("문의 등록 테스트")
    public void testRegister() {
        CourseInquiryDTO courseInquiryDTO = CourseInquiryDTO.builder()
                .courseId(1L)
                .memberId(1L)
                .inquiryTitle("Test Inquiry Title")
                .inquiryContent("Test Inquiry Content")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .inquiryStatus(InquiryStatus.PENDING)
                .inquiryGood(0L)
                .build();

        CourseInquiryDTO savedInquiry = courseInquiryService.register(courseInquiryDTO);

        // savedInquiry가 null이 아니고, 제대로 등록됐는지 확인
        assertThat(savedInquiry).isNotNull();
        assertThat(savedInquiry.getInquiryId()).isNotNull();

        // courseInquiryDTO와 savedInquiry의 제목을 비교
        assertThat(savedInquiry.getInquiryTitle()).isEqualTo(courseInquiryDTO.getInquiryTitle());
        log.info("Registered Inquiry: " + savedInquiry);
    }

    @Test
    @DisplayName("문의 전체 조회 테스트")
    void testReadAll() {
        List<CourseInquiry> inquiries = courseInquiryRepository.findAll();
        assertThat(inquiries).isNotEmpty();
    }

    @Test
    @DisplayName("문의 조회 테스트")
    void testRead(){
        Long inquiryId = 15L;

        CourseInquiryDTO foundInquiry = courseInquiryService.read(inquiryId);

        assertThat(foundInquiry).isNotNull();
        assertThat(foundInquiry.getInquiryId()).isEqualTo(inquiryId);
    }


    @Test
    @DisplayName("문의 수정 테스트")
    public void testUpdate() {
        // 초기 데이터 세팅
        CourseInquiryDTO courseInquiryDTO = CourseInquiryDTO.builder()
                .courseId(3L)
                .memberId(3L)
                .inquiryTitle("Update Test")
                .inquiryContent("Before update.")
                .inquiryStatus(InquiryStatus.PENDING)
                .createdDate(LocalDateTime.now())
                .build();

        // 문의 등록
        CourseInquiryDTO registeredDTO = courseInquiryService.register(courseInquiryDTO);


        // 수정할 데이터 설정
        registeredDTO.setInquiryTitle("Updated Title");
        registeredDTO.setInquiryContent("After update.");

        // 서비스 레벨에서 업데이트 실행
        CourseInquiryDTO updatedDTO = courseInquiryService.update(registeredDTO);

        // 업데이트 검증
        assertThat(updatedDTO.getInquiryTitle()).isEqualTo("Updated Title");
        assertThat(updatedDTO.getInquiryContent()).isEqualTo("After update.");

    }

    @Test
    @DisplayName("문의 삭제 테스트")
    public void testDelete() {
        CourseInquiry courseInquiry = CourseInquiry.builder()
                .courseId(2L)
                .memberId(2L)
                .inquiryTitle("Delete Test")
                .inquiryContent("This will be deleted.")
                .inquiryStatus(InquiryStatus.PENDING)
                .createdDate(LocalDateTime.now())
                .build();

        CourseInquiry savedCourseInquiry = courseInquiryRepository.save(courseInquiry);

        courseInquiryRepository.deleteById(savedCourseInquiry.getInquiryId());

        Optional<CourseInquiry> deletedCourseInquiry = courseInquiryRepository.findById(savedCourseInquiry.getInquiryId());
        assertThat(deletedCourseInquiry).isEmpty();
    }

}
