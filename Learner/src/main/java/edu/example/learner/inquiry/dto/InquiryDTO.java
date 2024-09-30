package edu.example.learner.inquiry.dto;

import edu.example.learner.inquiry.entity.Inquiry;
import edu.example.learner.inquiry.entity.InquiryStatus;
import edu.example.learner.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryDTO {
    private Long inquiryId;

    @NotEmpty
    private String inquiryTitle;

    @NotEmpty
    private String inquiryContent;

    private LocalDateTime inquiryCreateDate;

    private LocalDateTime inquiryUpdateDate;

    @Builder.Default
    private String inquiryStatus = InquiryStatus.CONFIRMING.name();

    private Long memberId;

    public InquiryDTO(Inquiry inquiry) {
        this.inquiryId = inquiry.getInquiryId();
        this.inquiryTitle = inquiry.getInquiryTitle();
        this.inquiryContent = inquiry.getInquiryContent();
        this.inquiryCreateDate = inquiry.getInquiryCreateDate();
        this.inquiryUpdateDate = inquiry.getInquiryUpdateDate();
        this.inquiryStatus = inquiry.getInquiryStatus();
        this.memberId = inquiry.getMember().getMemberId();
    }

    public Inquiry toEntity() {
        return Inquiry.builder()
                .inquiryId(inquiryId)
                .inquiryTitle(inquiryTitle)
                .inquiryContent(inquiryContent)
                .inquiryCreateDate(inquiryCreateDate)
                .inquiryUpdateDate(inquiryUpdateDate)
                .inquiryStatus(inquiryStatus)
                .member(Member.builder().memberId(memberId).build())
                .build();
    }
}
