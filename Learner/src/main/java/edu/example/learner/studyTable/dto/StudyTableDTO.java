package edu.example.learner.studyTable.dto;

import edu.example.learner.entity.Member;
import edu.example.learner.studyTable.entity.StudyTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyTableDTO {
    private Long studyTableId;

    private LocalDate studyDate;

    private int studyTime;

    private int completed;

    private Long memberId;

    public StudyTableDTO(StudyTable studyTable) {
        this.studyTableId = studyTable.getStudyTableId();
        this.studyDate = studyTable.getStudyDate();
        this.studyTime = studyTable.getStudyTime();
        this.completed = studyTable.getCompleted();
        this.memberId = studyTable.getMember().getMemberId();
    }

    public StudyTable toEntity() {
        return StudyTable.builder()
                .studyTableId(studyTableId)
                .studyDate(studyDate)
                .studyTime(studyTime)
                .completed(completed)
                .member(Member.builder().memberId(memberId).build())
                .build();
    }
}
