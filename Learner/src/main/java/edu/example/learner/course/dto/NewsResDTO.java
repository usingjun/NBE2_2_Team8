package edu.example.learner.course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsResDTO {
    private Long newsId;
    private String newsName;
    private String newsDescription;
    private LocalDateTime newsDate;
    private LocalDateTime lastModifiedDate;
}
