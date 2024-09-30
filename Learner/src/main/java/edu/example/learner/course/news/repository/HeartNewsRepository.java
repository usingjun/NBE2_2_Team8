package edu.example.learner.course.news.repository;

import edu.example.learner.course.news.entity.HeartNews;
import edu.example.learner.course.news.entity.NewsEntity;
import edu.example.learner.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartNewsRepository extends JpaRepository<HeartNews, Long> {
    Optional<HeartNews> findByMemberAndNewsEntity(Member member, NewsEntity newsEntity);
}
