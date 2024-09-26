package edu.example.learner.course.controller;

import edu.example.learner.course.dto.NewsResDTO;
import edu.example.learner.course.dto.NewsRqDTO;
import edu.example.learner.course.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course/{courseId}/news")
public class NewsController {
    private final NewsService newsService;

    // 새소식 등록
    @PostMapping
    public ResponseEntity<NewsResDTO> createNews(@PathVariable Long courseId, @Validated @RequestBody NewsRqDTO newsRqDTO) {
        NewsResDTO createdNews = newsService.createNews(courseId, newsRqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNews);
    }

    // 새소식 업데이트
    @PutMapping("/{newsId}")
    public ResponseEntity<NewsResDTO> updateNews(@PathVariable Long coureseId,
                                                 @PathVariable Long newsId,
                                                 @Validated @RequestBody NewsRqDTO newsRqDTO) {
        NewsResDTO updatedNews = newsService.updateNews(coureseId, newsId, newsRqDTO);
        return ResponseEntity.ok().body(updatedNews);
    }

    // 새소식 삭제
    @DeleteMapping("/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable Long courseId,
                                        @PathVariable Long newsId) {
        newsService.deleteNews(courseId, newsId);
        return ResponseEntity.ok().build();
    }

    // 특정 새소식 조회
    @GetMapping("/{newsId}")
    public ResponseEntity<NewsResDTO> getNews(@PathVariable Long courseId,
                                              @PathVariable Long newsId) {
        NewsResDTO news = newsService.getNews(courseId, newsId);
        return ResponseEntity.ok().body(news);
    }

    // 전체 새소식 조회
    @GetMapping
    public ResponseEntity<List<NewsResDTO>> getAllNews(@PathVariable Long courseId) {
        List<NewsResDTO> allNews = newsService.getAllNews(courseId);
        return ResponseEntity.ok().body(allNews);
    }

}
