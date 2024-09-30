package edu.example.learner.course.repository;

import edu.example.learner.course.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
