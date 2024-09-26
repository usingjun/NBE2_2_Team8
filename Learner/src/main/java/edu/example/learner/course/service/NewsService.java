package edu.example.learner.course.service;

import edu.example.learner.course.dto.NewsResDTO;
import edu.example.learner.course.dto.NewsRqDTO;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.NewsEntity;
import edu.example.learner.course.repository.CourseRepository;
import edu.example.learner.course.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsRepository newsRepository;
    private final CourseRepository courseRepository;

    public NewsResDTO createNews(Long courseId, NewsRqDTO newsRqDTO) {
        log.info("새소식 등록 : {}", newsRqDTO.getNewsName());
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다."));
        NewsEntity newsEntity = newsRqDTO.toEntity();
        newsEntity.changeCourse(course);
        newsRepository.save(newsEntity);

        return NewsResDTO.fromEntity(newsEntity);
    }

    public NewsResDTO updateNews(Long courseId, Long newsId, NewsRqDTO newsRqDTO) {
        log.info("새소식 업데이트 : {}", newsRqDTO.getNewsName());

        // 검증 메서드 호출
        NewsEntity newsEntity = validateNewsInCourse(courseId, newsId);
        //업데이트
        newsEntity.changeNewsName(newsRqDTO.getNewsName());
        newsEntity.changeNewsDescription(newsRqDTO.getNewsDescription());

        return NewsResDTO.fromEntity(newsRepository.save(newsEntity));
    }

    public void deleteNews(Long courseId, Long newsId) {
        log.info("새소식 삭제: {}", newsId);

        // 검증 메서드 호출
        NewsEntity newsEntity = validateNewsInCourse(courseId, newsId);

        newsRepository.deleteById(newsId);
    }

    @Transactional(readOnly = true)
    public NewsResDTO getNews(Long courseId, Long newsId) {
        log.info("새소식 조회 : {}", newsId);

        // 검증 메서드 호출
        NewsEntity newsEntity = validateNewsInCourse(courseId, newsId);

        return NewsResDTO.fromEntity(newsEntity);
    }

    @Transactional(readOnly = true)
    public List<NewsResDTO> getAllNews(Long courseId) {
        log.info("전체 새소식 조회: 강의 ID {}", courseId);

        // 해당 강의에 속하는 모든 새소식 조회
        List<NewsEntity> newsEntities = newsRepository.findAllNewsByCourse(courseId);

        return newsEntities.stream()
                .map(NewsResDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private NewsEntity validateNewsInCourse(Long courseId, Long newsId) {
        NewsEntity newsEntity = newsRepository.findById(newsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 소식을 찾을 수 없습니다."));

        if (!newsEntity.getCourseNews().getCourseId().equals(courseId)) {
            throw new IllegalArgumentException("해당 강의에 속하지 않는 소식입니다.");
        }

        return newsEntity;
    }
}
