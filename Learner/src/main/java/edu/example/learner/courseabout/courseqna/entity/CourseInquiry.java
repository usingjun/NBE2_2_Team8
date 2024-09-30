package edu.example.learner.courseabout.courseqna.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name="course_inquiry")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CourseInquiry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;         //문의 Id


    private Long courseId;          //문의 강의 Id
    private Long memberId;          //문의자 Id
    private String inquiryTitle;    //문의 제목
    private String inquiryContent;  //문의 내용

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @Enumerated(EnumType.STRING)
    private InquiryStatus inquiryStatus;

    private Long inquiryGood;       //문의 좋아요 수




    public void changeInqueryTitle(String inquiryTitle){
        this.inquiryTitle = inquiryTitle;
    }

    public void changeInquiryContent(String inquiryContent){
        this.inquiryContent = inquiryContent;
    }

    public void changeInquiryStatus(InquiryStatus inquiryStatus){
        this.inquiryStatus = inquiryStatus;
    }

    public void changeInquiryGood(Long inquiryGood){
        this.inquiryGood = inquiryGood;
    }
}