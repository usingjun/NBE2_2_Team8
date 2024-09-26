package edu.example.learner.course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsRqDTO {
    // 수정, 삭제시 필요
    private Long newsId;

    @NotBlank(message = "새소식을 적어주세요.")
    private String newsName;

    private String newsDescription;
}
