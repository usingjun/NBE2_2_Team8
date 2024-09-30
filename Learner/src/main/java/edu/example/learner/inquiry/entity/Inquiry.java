package edu.example.learner.inquiry.entity;

import edu.example.learner.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

    @LastModifiedDate
    private LocalDateTime inquiryUpdateDate;
    
    private String inquiryStatus;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void changeInquiryTitle(String inquiryTitle) {
        this.inquiryTitle = inquiryTitle;
    }

    public void changeInquiryContent(String inquiryContent) {
        this.inquiryContent = inquiryContent;
    }

    public void changeInquiryStatus(String inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }
}
