package edu.example.learner.course.controller;

import edu.example.learner.course.dto.NewsResDTO;
import edu.example.learner.course.dto.NewsRqDTO;
import edu.example.learner.course.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<NewsResDTO> updateNews(@PathVariable Long courseId,
                                                 @PathVariable Long newsId,
                                                 @Validated @RequestBody NewsRqDTO newsRqDTO) {
        NewsResDTO updatedNews = newsService.updateNews(courseId, newsId, newsRqDTO);
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
                                              @PathVariable Long newsId,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {

        //조회수 추가
        newsService.addViewCount(request, response, courseId);

        NewsResDTO news = newsService.getNews(courseId, newsId);
        return ResponseEntity.ok().body(news);
    }

    // 전체 새소식 조회
    @GetMapping
    public ResponseEntity<Page<NewsResDTO>> getAllNews(@PathVariable Long courseId,
                                                       @PageableDefault(size = 8, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NewsResDTO> allNews = newsService.getAllNews(courseId, pageable);
        return ResponseEntity.ok().body(allNews);
    }

}
