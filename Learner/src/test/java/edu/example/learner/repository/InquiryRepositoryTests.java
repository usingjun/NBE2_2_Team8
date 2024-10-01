package edu.example.learner.repository;

import edu.example.learner.qna.inquiry.entity.Inquiry;
import edu.example.learner.qna.inquiry.entity.InquiryStatus;
import edu.example.learner.member.entity.Member;
import edu.example.learner.qna.inquiry.repository.InquiryRepository;
import lombok.extern.log4j.Log4j2;
import edu.example.learner.member.repository.MemberRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryRepositoryTests {
    @Autowired
    private InquiryRepository inquiryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Order(1)
    @Rollback(false)
    void testInsert() {
        //GIVEN
        memberRepository.save(Member.builder().build());
        Inquiry inquiry = Inquiry.builder()
                .inquiryTitle("inquiry test title")
                .inquiryContent("inquiry test content")
                .inquiryStatus(InquiryStatus.CONFIRMING.name())
                .member(Member.builder().memberId(1L).build())
                .build();

        //WHEN
        Inquiry savedInquiry = inquiryRepository.save(inquiry);

        //THEN
        assertNotNull(savedInquiry);
        assertEquals(1L, savedInquiry.getInquiryId());
        assertEquals("inquiry test title", savedInquiry.getInquiryTitle());
        assertEquals("inquiry test content", savedInquiry.getInquiryContent());

        log.info("--- savedInquiry : " + savedInquiry);
    }

    @Test
    @Order(2)
    void testRead() {
        //GIVEN
        Long inquiryId = 1L;

        //WHEN
        Optional<Inquiry> foundInquiry = inquiryRepository.findById(inquiryId);

        //THEN
        assertNotNull(foundInquiry);
        assertEquals(inquiryId, foundInquiry.get().getInquiryId());

        log.info("--- foundInquiry : " + foundInquiry);
    }

    @Test
    @Order(3)
    void testUpdate() {
        //GIVEN
        Long inquiryId = 1L;
        Inquiry inquiry = inquiryRepository.findById(inquiryId).get();

        //WHEN
        inquiry.changeInquiryTitle("new inquiry title");
        inquiry.changeInquiryContent("new inquiry content");
        inquiry.changeInquiryStatus(String.valueOf(InquiryStatus.RESOLVED));
        Inquiry updatedInquiry = inquiryRepository.findById(inquiryId).get();

        //THEN
        assertEquals("new inquiry title", inquiry.getInquiryTitle());
        assertEquals("new inquiry content", inquiry.getInquiryContent());
        assertEquals(InquiryStatus.RESOLVED.name(), inquiry.getInquiryStatus());

        log.info("--- updatedInquiry : " + updatedInquiry);
    }

    @Test
    @Order(4)
    void testDelete() {
        //GIVEN
        Long inquiryId = 1L;
        assertTrue(inquiryRepository.existsById(inquiryId));

        //WHEN
        inquiryRepository.deleteById(inquiryId);

        //THEN
        assertFalse(inquiryRepository.findById(inquiryId).isPresent());
    }
}
