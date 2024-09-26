package edu.example.learner.course.repository;

import edu.example.learner.course.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    @Query("SELECT n FROM NewsEntity n JOIN FETCH n.courseNews WHERE n.courseNews.courseId = :courseId")
    List<NewsEntity> findAllNewsByCourse(@Param("courseId") Long courseId);

}
