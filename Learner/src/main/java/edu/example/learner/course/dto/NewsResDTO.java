package edu.example.learner.course.dto;

import edu.example.learner.course.entity.NewsEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsResDTO {
    private Long newsId;
    private String newsName;
    private String newsDescription;
    private LocalDateTime newsDate;
    private LocalDateTime lastModifiedDate;

    public static NewsResDTO fromEntity(NewsEntity entity) {
        return NewsResDTO.builder()
                .newsId(entity.getNewsId())
                .newsName(entity.getNewsName())
                .newsDescription(entity.getNewsDescription())
                .newsDate(entity.getNewsDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }
}
