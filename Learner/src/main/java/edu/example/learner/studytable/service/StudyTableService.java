package edu.example.learner.studytable.service;

import edu.example.learner.studytable.dto.StudyTableDTO;

import java.time.LocalDate;
import java.util.List;

public interface StudyTableService {

    StudyTableDTO read(Long studyTableId);

    StudyTableDTO register(StudyTableDTO studyTableDTO);

    StudyTableDTO update(StudyTableDTO studyTableDTO);

    StudyTableDTO delete(Long studyTableId);

    int getWeeklyStudyTime(Long memberId, LocalDate startDate, LocalDate endDate);

    int getYearlyStudyTime(Long memberId, int year);

    int getWeeklyCompleted(Long memberID, LocalDate startDate, LocalDate endDate);

    int getMonthlyCompleted(Long memberID, int month);

    int getYearlyCompleted(Long memberID, int year);

    List<Object[]> getWeeklySummary(Long memberId, LocalDate startDate, LocalDate endDate);

    List<Object[]> getYearlySummary(Long memberId, int year);
}
