package edu.example.learner.courseabout.video.service;

import edu.example.learner.courseabout.video.entity.Video;
import edu.example.learner.courseabout.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

private final VideoRepository videoRepository;

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Video getVideoById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }

    public Video saveVideo(Video video) {
        return videoRepository.save(video);
    }

    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }
}
