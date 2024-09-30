package edu.example.learner.course.dto;

import edu.example.learner.course.entity.CourseAnswer;
import edu.example.learner.course.entity.CourseInquiry;
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

    public CourseAnswerDTO(CourseAnswer answer) {
        this.answerId = answer.getAnswerId();
        this.answerContent = answer.getAnswerContent();
        this.answerCreateDate = answer.getAnswerCreateDate();
        this.inquiryId = answer.getCourseInquiry().getInquiryId();
    }

    public CourseAnswer toEntity() {
        return CourseAnswer.builder()
                .answerId(answerId)
                .answerContent(answerContent)
                .answerCreateDate(answerCreateDate)
                .courseInquiry(CourseInquiry.builder().inquiryId(inquiryId).build())
                .build();
    }
}
