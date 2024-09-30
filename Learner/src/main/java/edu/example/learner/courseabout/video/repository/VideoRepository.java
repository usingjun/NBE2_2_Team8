package edu.example.learner.courseabout.video.repository;

import edu.example.learner.courseabout.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
