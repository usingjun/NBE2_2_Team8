package edu.example.learner.course.service;

import edu.example.learner.course.dto.NewsResDTO;
import edu.example.learner.course.dto.NewsRqDTO;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.NewsEntity;
import edu.example.learner.course.repository.CourseRepository;
import edu.example.learner.course.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class NewsServiceTest {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private NewsService newsService;
    private NewsEntity initialNews;
    private Course savedCourse;

    @BeforeEach
    public void setUp() {
        // 테스트를 위한 초기 데이터 설정
        Course course = new Course();
        course.changeCourseName("초기 강의");
        course.changeCourseDescription("초기 강의 설명");
        course.changeInstructorName("초기 강사");
        course.changePrice(30000L);
        course.changeCourseLevel(1);
        course.changeSale(false);

        savedCourse = courseRepository.save(course);

        NewsRqDTO newsRqDTO = new NewsRqDTO();
        newsRqDTO.setNewsName("초기 소식 제목");
        newsRqDTO.setNewsDescription("초기 소식 내용");

        // 뉴스 엔티티 등록
        initialNews = newsRepository.save(newsRqDTO.toEntity());
        initialNews.changeCourse(savedCourse);  // 강의와 연결
    }

    @Test
    public void registerNews() {
        // Given
        Course course = new Course();
        course.changeCourseName("새 강의");
        course.changeCourseDescription("새 강의 설명");
        course.changeInstructorName("새 강사");
        course.changePrice(20000L);
        course.changeCourseLevel(2);
        course.changeSale(true);

        Course saveCourse = courseRepository.save(course);

        NewsRqDTO newsRqDTO = new NewsRqDTO();
        newsRqDTO.setNewsName("새소식 제목");
        newsRqDTO.setNewsDescription("새소식 내용");

        // When
        NewsResDTO newsResDTO = newsService.createNews(saveCourse.getCourseId(), newsRqDTO);

        // Then
        assertThat(newsResDTO).isNotNull();
        assertThat(newsResDTO.getNewsName()).isEqualTo("새소식 제목");
    }

    @Test
    public void updateNewsTest() {
        // Given
        Long newsId = initialNews.getNewsId();
        NewsRqDTO newsRqDTO = new NewsRqDTO();
        newsRqDTO.setNewsName("업데이트된 소식 제목");
        newsRqDTO.setNewsDescription("업데이트된 소식 내용");

        // When
        NewsResDTO updatedNewsResDTO = newsService.updateNews(savedCourse.getCourseId(), newsId, newsRqDTO);

        // Then
        assertThat(updatedNewsResDTO).isNotNull();
        assertThat(updatedNewsResDTO.getNewsName()).isEqualTo("업데이트된 소식 제목");
    }

    @Test
    public void deleteNewsTest() {
        // Given
        Long newsId = initialNews.getNewsId();

        // When
        newsService.deleteNews(savedCourse.getCourseId(), newsId);

        // Then
        assertThat(newsRepository.findById(newsId)).isEmpty();
    }

    @Test
    public void getNews() {
        // When
        NewsResDTO foundNews = newsService.getNews(savedCourse.getCourseId(), initialNews.getNewsId());

        log.info("출력 {}", foundNews);

        // Then
        assertThat(foundNews).isNotNull();
        assertThat(foundNews.getNewsName()).isEqualTo("초기 소식 제목");
    }

    @Test
    public void getAllNews() {
        // Given
        NewsRqDTO newsRqDTO1 = new NewsRqDTO();
        newsRqDTO1.setNewsName("새소식 1");
        newsRqDTO1.setNewsDescription("내용 1");

        NewsEntity news1 = newsRepository.save(newsRqDTO1.toEntity());
        news1.changeCourse(savedCourse); // 강의와 연결

        NewsRqDTO newsRqDTO2 = new NewsRqDTO();
        newsRqDTO2.setNewsName("새소식 2");
        newsRqDTO2.setNewsDescription("내용 2");

        NewsEntity news2 = newsRepository.save(newsRqDTO2.toEntity());
        news2.changeCourse(savedCourse); // 강의와 연결

        // When
        List<NewsResDTO> allNews = newsService.getAllNews(savedCourse.getCourseId());

//        for (NewsResDTO allNew : allNews) {
//            log.info("출력: {}", allNew);
//        }

        // Then
        assertThat(allNews).isNotEmpty();
        assertThat(allNews.size()).isGreaterThan(1); // 초기 소식 포함
    }

}