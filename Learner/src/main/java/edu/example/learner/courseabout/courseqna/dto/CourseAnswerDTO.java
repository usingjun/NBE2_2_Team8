package edu.example.learner.courseabout.courseqna.dto;

import edu.example.learner.courseabout.courseqna.entity.CourseAnswer;
import edu.example.learner.courseabout.courseqna.entity.CourseInquiry;
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
public class CourseAnswerDTO {
    private Long answerId;

    @NotEmpty
    private String answerContent;

    private LocalDateTime answerCreateDate;

    private Long inquiryId;
    private Long memberId;

    public CourseAnswerDTO(CourseAnswer answer) {
        this.answerId = answer.getAnswerId();
        this.answerContent = answer.getAnswerContent();
        this.answerCreateDate = answer.getAnswerCreateDate();
        this.inquiryId = answer.getCourseInquiry().getInquiryId();
        this.memberId = answer.getMemberId();
    }

    public CourseAnswer toEntity() {
        return CourseAnswer.builder()
                .answerId(answerId)
                .memberId(memberId)
                .answerContent(answerContent)
                .answerCreateDate(answerCreateDate)
                .courseInquiry(CourseInquiry.builder().inquiryId(inquiryId).build())
                .build();
    }
}
