package edu.example.learner.course.entity;

import java.time.LocalDateTime;

public interface Inquiry {
    Long inquiryId = null;
    Long memberId = null;
    String inquiryTitle = null;
    String inquiryContent = null;
    LocalDateTime createdDate = null;
    LocalDateTime updateTime = null;
    InquiryStatus inquiryStatus = null;
    Long inquiryGood = null;

    void changeInqueryTitle(String title);
    void changeInquiryContent(String content);
    void changeInquiryStatus(InquiryStatus inquiryStatus);
    void changeInquiryGood(Long good);
}
