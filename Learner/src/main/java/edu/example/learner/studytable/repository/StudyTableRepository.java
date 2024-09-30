package edu.example.learner.studytable.repository;

import edu.example.learner.studytable.entity.StudyTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface StudyTableRepository extends JpaRepository<StudyTable, Long> {

    //주간 학습 시간
    @Query("select sum(st.studyTime) from StudyTable st where st.member.memberId = :memberId and st.studyDate between :startDate and :endDate")
    int getWeeklyStudyTime(@Param("memberId") Long memberId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //연간 학습 시간
    @Query("select sum(st.studyTime) from StudyTable st where st.member.memberId =:memberId and year(st.studyDate) = :year")
    int getYearlyStudyTime(@Param("memberId") Long memberID, @Param("year") int year);

    //주간 완료한 수업
    @Query("select sum(st.completed) from StudyTable st where st.member.memberId = :memberId and st.studyDate between :startDate and :endDate")
    int getWeeklyCompleted(@Param("memberId") Long memberID, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //월간 완료한 수업
    @Query("select sum(st.completed) from StudyTable st where st.member.memberId = :memberId and month(st.studyDate) = :month")
    int getMonthlyCompleted(@Param("memberId") Long memberID, @Param("month") int month);

    //연간 완료한 수업
    @Query("select sum(st.completed) from StudyTable st where st.member.memberId =:memberId and year(st.studyDate) = :year")
    int getYearlyCompleted(@Param("memberId") Long memberID, @Param("year") int year);

}
