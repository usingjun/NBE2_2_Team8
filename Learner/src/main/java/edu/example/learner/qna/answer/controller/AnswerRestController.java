package edu.example.learner.qna.answer.controller;

import edu.example.learner.qna.answer.dto.AnswerDTO;
import edu.example.learner.qna.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerRestController {
    private final AnswerService answerService;

    @GetMapping("/{inquiryId}")
    public ResponseEntity<AnswerDTO> read(@PathVariable("inquiryId") Long inquiryId) {
        return ResponseEntity.ok(answerService.read(inquiryId));
    }

    @GetMapping
    public ResponseEntity<List<AnswerDTO>> readAll() {
        return ResponseEntity.ok(answerService.readAll());
    }

    @PostMapping
    public ResponseEntity<AnswerDTO> create(@Validated @RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok(answerService.register(answerDTO));
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<AnswerDTO> update(@PathVariable("answerId") Long answerId, @Validated @RequestBody AnswerDTO answerDTO) {
        answerDTO.setAnswerId(answerId);
        return ResponseEntity.ok(answerService.update(answerDTO));
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("answerId") Long answerId) {
        answerService.delete(answerId);
        return ResponseEntity.ok(Map.of("result", "success"));
    }
}
