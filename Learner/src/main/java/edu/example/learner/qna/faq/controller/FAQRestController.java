package edu.example.learner.qna.faq.controller;

import edu.example.learner.qna.faq.dto.FAQDTO;
import edu.example.learner.qna.faq.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/faqs")
@RequiredArgsConstructor
public class FAQRestController {
    private final FAQService faqService;

    @GetMapping("/{faqId}")
    public ResponseEntity<FAQDTO> read(@PathVariable("faqId") Long faqId) {
        return ResponseEntity.ok(faqService.read(faqId));
    }

    @GetMapping
    public ResponseEntity<List<FAQDTO>> readAll() {
        return ResponseEntity.ok(faqService.readAll());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FAQDTO>> readByCategory(@PathVariable("category") String category) {
        String categoryUpperCase = category.toUpperCase();
        return ResponseEntity.ok(faqService.readByCategory(categoryUpperCase));
    }

    @PostMapping
    public ResponseEntity<FAQDTO> create(@Validated @RequestBody FAQDTO faqDTO) {
        return ResponseEntity.ok(faqService.register(faqDTO));
    }

    @PutMapping("/{faqId}")
    public ResponseEntity<FAQDTO> update(@PathVariable("faqId") Long faqId, @Validated @RequestBody FAQDTO faqDTO) {
        faqDTO.setFaqId(faqId);
        return ResponseEntity.ok(faqService.update(faqDTO));
    }

    @DeleteMapping("/{faqId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("faqId") Long faqId) {
        faqService.delete(faqId);
        return ResponseEntity.ok(Map.of("result", "success"));
    }
}
