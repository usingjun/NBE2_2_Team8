package edu.example.learner.course.repository;

import edu.example.learner.course.entity.HeartNews;
import edu.example.learner.course.entity.NewsEntity;
import edu.example.learner.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HeartNewsRepository extends JpaRepository<HeartNews, Long> {
    Optional<HeartNews> findByMemberAndNewsEntity(Member member, NewsEntity newsEntity);
}
