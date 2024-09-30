package edu.example.learner.studyTable.service;

import edu.example.learner.studyTable.dto.StudyTableDTO;

import java.time.LocalDate;
import java.util.Map;

public interface StudyTableService {

    int getWeeklyStudyTime(Long memberId, LocalDate startDate, LocalDate endDate);

    int getYearlyStudyTime(Long memberId, int year);

    StudyTableDTO read();
}
