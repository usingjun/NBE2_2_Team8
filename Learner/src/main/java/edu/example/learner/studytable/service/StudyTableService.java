package edu.example.learner.studytable.service;

import edu.example.learner.studytable.dto.StudyTableDTO;

import java.time.LocalDate;

public interface StudyTableService {

    int getWeeklyStudyTime(Long memberId, LocalDate startDate, LocalDate endDate);

    int getYearlyStudyTime(Long memberId, int year);

    StudyTableDTO read();
}
