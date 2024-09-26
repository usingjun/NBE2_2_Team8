package edu.example.learner.service;

import edu.example.learner.dto.AnswerDTO;
import edu.example.learner.entity.Inquiry;
import edu.example.learner.entity.InquiryStatus;
import edu.example.learner.entity.Member;
import edu.example.learner.repository.AnswerRepository;
import edu.example.learner.repository.InquiryRepository;
import edu.example.learner.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnswerServiceTests {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private InquiryRepository inquiryRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    public void setUp() {
        Member member = memberRepository.save(Member.builder().memberId(1L).build());
    }

    @Test
    @Order(1)
    void testRegister() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            //GIVEN
            inquiryRepository.save(Inquiry.builder().inquiryId((long) i).member(Member.builder().memberId(1L).build()).build());
            AnswerDTO answerDTO = AnswerDTO.builder()
                    .answerContent("content" + i)
                    .inquiryId((long) i)
                    .build();

            //WHEN
            AnswerDTO registeredAnswerDTO = answerService.register(answerDTO);

            //THEN
            assertNotNull(registeredAnswerDTO);
            assertEquals(i, registeredAnswerDTO.getAnswerId());
            assertEquals(InquiryStatus.ANSWERED.name(), inquiryRepository.findById(registeredAnswerDTO.getInquiryId()).get().getInquiryStatus());

            log.info("--- registeredAnswerDTO: " + registeredAnswerDTO);
        });
    }

    @Test
    @Order(2)
    void testRead() {
        //GIVEN
        Long answerId = 1L;

        //WHEN
        AnswerDTO foundAnswerDTO = answerService.read(answerId);

        //THEN
        assertNotNull(foundAnswerDTO);
        assertEquals(answerId, foundAnswerDTO.getAnswerId());

        log.info("--- foundAnswerDTO: " + foundAnswerDTO);
    }

    @Test
    @Order(3)
    void testReadAll() {
        //GIVEN

        //WHEN
        List<AnswerDTO> answerDTOList = answerService.readAll();

        //THEN
        assertNotNull(answerDTOList);
        assertEquals(10, answerDTOList.size());

        answerDTOList.forEach(System.out::println);
    }

    @Test
    @Order(4)
    void testUpdate() {
        //GIVEN
        AnswerDTO answerDTO = AnswerDTO.builder()
                .answerId(10L)
                .answerContent("new content")
                .build();

        //WHEN
        AnswerDTO updatedAnswerDTO = answerService.update(answerDTO);

        //THEN
        assertNotNull(updatedAnswerDTO);
        assertEquals("new content", updatedAnswerDTO.getAnswerContent());

        log.info("--- updatedAnswerDTO: " + updatedAnswerDTO);
    }

    @Test
    @Order(5)
    void testDelete() {
        //GIVEN
        Long answerId = 9L;

        //WHEN
        answerService.delete(answerId);

        //THEN
        assertFalse(answerRepository.existsById(answerId));
    }
}
