package edu.example.learner.course.news.controller;

import edu.example.learner.course.news.dto.HeartNewsReqDTO;
import edu.example.learner.course.news.dto.NewsResDTO;
import edu.example.learner.course.news.dto.NewsRqDTO;
import edu.example.learner.course.news.service.HeartNewsService;
import edu.example.learner.course.news.service.NewsService;
import edu.example.learner.redis.RedisViewServiceImpl;
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

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course/{courseId}/news")
public class NewsController {
    private final NewsService newsService;
    private final HeartNewsService heartNewsService;
    private final RedisViewServiceImpl redisViewServiceImpl;

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
                                              HttpServletRequest request) {
        NewsResDTO news = newsService.getNews(courseId, newsId);

        //조회수 올리기
        String ipAddress = request.getRemoteAddr(); // IP주소
        String redisKey = "viewNews:" + newsId + ":-" + ipAddress;

        // 레디스 중복조회 여부확인
        boolean isDuplicate = redisViewServiceImpl.isDuplicateView(redisKey, Duration.ofHours(24));
        if (!isDuplicate) {
            // 중복이 아닐시 조회수 증가
            newsService.addViewCountV2(newsId);
        }


        return ResponseEntity.ok().body(news);
    }

    // 전체 새소식 조회
    @GetMapping
    public ResponseEntity<Page<NewsResDTO>> getAllNews(@PathVariable Long courseId,
                                                       @PageableDefault(size = 8, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NewsResDTO> allNews = newsService.getAllNews(courseId, pageable);
        return ResponseEntity.ok().body(allNews);
    }

    //좋아요 처리
    // courseId 처리는 고려
    @PatchMapping
    public ResponseEntity<?> increaseHeart(@RequestBody @Validated HeartNewsReqDTO heartNewsReqDTO) throws Exception {
        heartNewsService.insert(heartNewsReqDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> decreaseHeart(@RequestBody @Validated HeartNewsReqDTO heartNewsReqDTO) {
        heartNewsService.delete(heartNewsReqDTO);
        return ResponseEntity.ok().build();
    }
}
