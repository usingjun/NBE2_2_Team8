package edu.example.learner.course.controller;

import edu.example.learner.course.dto.NewsResDTO;
import edu.example.learner.course.dto.NewsRqDTO;
import edu.example.learner.course.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courese/news")
public class NewsController {
    private final NewsService newsService;

    @PostMapping("/{courseId}")
    public ResponseEntity<NewsResDTO> createNews(@PathVariable Long courseId, @Validated @RequestBody NewsRqDTO newsRqDTO) {
        NewsResDTO createdNews = newsService.createNews(courseId, newsRqDTO);
        return new ResponseEntity<>(createdNews, HttpStatus.CREATED);
    }


}
