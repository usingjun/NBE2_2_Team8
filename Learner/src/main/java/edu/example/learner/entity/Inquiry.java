package edu.example.learner.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    private String inquiryTitle;

    private String inquiryContent;

    @CreatedDate
    private LocalDateTime inquiryCreateDate;

    private String inquiryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void changeInquiryTitle(String inquiryTitle) {
        this.inquiryTitle = inquiryTitle;
    }

    public void changeInquiryContent(String inquiryContent) {
        this.inquiryContent = inquiryContent;
    }

    public void changeInquiryStatus(InquiryStatus inquiryStatus) {
        this.inquiryStatus = inquiryStatus.name();
    }
}
