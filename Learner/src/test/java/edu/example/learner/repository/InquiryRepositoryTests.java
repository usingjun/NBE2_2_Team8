package edu.example.learner.repository;

import edu.example.learner.entity.Inquiry;
import edu.example.learner.entity.InquiryStatus;
import edu.example.learner.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class InquiryRepositoryTests {
    @Autowired
    private InquiryRepository inquiryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
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
        assertEquals(1, savedInquiry.getInquiryId());
        assertEquals("inquiry test title", savedInquiry.getInquiryTitle());
        assertEquals("inquiry test content", savedInquiry.getInquiryContent());

        log.info(savedInquiry);
    }

    @Test
    @Transactional
    void testRead() {
        //GIVEN
        Long inquiryId = 1L;

        //WHEN
        Optional<Inquiry> foundInquiry = inquiryRepository.findById(inquiryId);

        //THEN
        assertNotNull(foundInquiry);
        assertEquals(inquiryId, foundInquiry.get().getInquiryId());

        log.info(foundInquiry);
    }

    @Test
    @Transactional
    @Commit
    void testUpdate() {
        //GIVEN
        Long inquiryId = 1L;
        Inquiry inquiry = inquiryRepository.findById(inquiryId).get();

        //WHEN
        inquiry.changeInquiryTitle("new inquiry title");
        inquiry.changeInquiryContent("new inquiry content");
        inquiry.changeInquiryStatus(InquiryStatus.RESOLVED);

        //THEN
        assertEquals("new inquiry title", inquiry.getInquiryTitle());
        assertEquals("new inquiry content", inquiry.getInquiryContent());
        assertEquals(InquiryStatus.RESOLVED.name(), inquiry.getInquiryStatus());
    }
}
