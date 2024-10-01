package edu.example.learner.studytable.controller;

import edu.example.learner.studytable.dto.StudyTableDTO;
import edu.example.learner.studytable.service.StudyTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/study-tables")
@RequiredArgsConstructor
public class StudyTableController {
    private final StudyTableService studyTableService;

    @GetMapping("/{memberId}/weekly-summary")
    public ResponseEntity<?> readWeeklySummary(@PathVariable("memberId") Long memberId) {
        Map<String, Object> response = new HashMap<>();
        response.put("weeklyStudyTime", studyTableService.getWeeklyStudyTime(memberId, LocalDate.now()));
        response.put("weeklyCompleted", studyTableService.getWeeklyCompleted(memberId, LocalDate.now()));
        response.put("weeklySummary", studyTableService.getWeeklySummary(memberId, LocalDate.now()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberId}/yearly-summary")
    public ResponseEntity<?> readYearlySummary(@PathVariable("memberId") Long memberId) {
        Map<String, Object> response = new HashMap<>();
        response.put("yearlyCompleted", studyTableService.getYearlyCompleted(memberId, LocalDate.now().getYear()));
        response.put("yearlyStudyTime", studyTableService.getYearlyStudyTime(memberId, LocalDate.now().getYear()));
        response.put("yearlySummary", studyTableService.getYearlySummary(memberId, LocalDate.now().getYear()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<StudyTableDTO> create(@Validated @RequestBody StudyTableDTO studyTableDTO) {
        return ResponseEntity.ok(studyTableService.register(studyTableDTO));
    }

    @PutMapping("/{studyTableId}")
    public ResponseEntity<StudyTableDTO> update(@Validated @RequestBody StudyTableDTO studyTableDTO, @PathVariable("studyTableId") Long studyTableId) {
        studyTableDTO.setStudyTableId(studyTableId);
        return ResponseEntity.ok(studyTableService.update(studyTableDTO));
    }

    @DeleteMapping("/{studyTableId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("studyTableId") Long studyTableId) {
        studyTableService.delete(studyTableId);
        return ResponseEntity.ok(Map.of("result", "success"));
    }
}
