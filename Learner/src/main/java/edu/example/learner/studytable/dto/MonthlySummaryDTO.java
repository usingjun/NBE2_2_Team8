package edu.example.learner.studytable.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlySummaryDTO {
    private int month;
    private int week;
    private int totalCompleted;
}
