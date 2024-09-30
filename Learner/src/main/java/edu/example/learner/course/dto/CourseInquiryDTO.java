package edu.example.learner.course.dto;

import edu.example.learner.course.entity.CourseInquiry;
import edu.example.learner.course.entity.InquiryStatus;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseInquiryDTO {
    private Long inquiryId;
    private Long courseId;
    private Long memberId;
    private String inquiryTitle;
    private String inquiryContent;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;


    private InquiryStatus inquiryStatus;

    @Min(0)
    private Long inquiryGood;

    public CourseInquiry toEntity(){
        CourseInquiry courseInquiry = CourseInquiry.builder()
                .courseId(courseId)
                .memberId(memberId)
                .inquiryTitle(inquiryTitle)
                .inquiryContent(inquiryContent)
                .inquiryStatus(inquiryStatus)
                .build();

        return courseInquiry;
    }

    public CourseInquiryDTO(CourseInquiry ci){
        this.inquiryId = ci.getInquiryId();
        this.courseId = ci.getCourseId();
        this.memberId = ci.getMemberId();
        this.inquiryTitle = ci.getInquiryTitle();
        this.inquiryContent = ci.getInquiryContent();
        this.createdDate = ci.getCreatedDate();
        this.updateDate = ci.getUpdateTime();
        this.inquiryStatus = ci.getInquiryStatus();
        this.inquiryGood = ci.getInquiryGood();
    }
}
