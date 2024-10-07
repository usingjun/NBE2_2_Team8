package edu.example.learner.courseabout.video.controller;

import edu.example.learner.courseabout.video.dto.VideoDTO;
import edu.example.learner.courseabout.video.entity.Video;
import edu.example.learner.courseabout.video.repository.VideoRepository;
import edu.example.learner.courseabout.video.service.VideoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    private final VideoRepository videoRepository;

    private double totalDuration = 0; // 전체 동영상 시간
    private double currentTime = 0; // 현재 재생 시간

    @PostMapping("/savePlayTime")
    public String savePlayTime(@RequestBody PlayTimeRequest playTimeRequest) {
        Long videoId = playTimeRequest.getVideoId();

        Optional<VideoDTO> videoById = videoService.getVideoById(videoId);
        if (videoById.isPresent()) {
            Video video = videoById.get().toEntity();

            if (playTimeRequest.getTotalDuration() > 0) {
                video.setTotalVideoDuration((long) playTimeRequest.getTotalDuration()); // 전체 동영상 시간 업데이트
                System.out.println("전체 동영상 시간: " + video.getTotalVideoDuration() + "초");
            } else {
                video.setCurrentVideoTime((long) playTimeRequest.getCurrentTime()); // 현재 재생 시간 업데이트
                System.out.println("현재 동영상 재생 시간: " + video.getCurrentVideoTime() + "초");
            }

            videoRepository.save(video); // 변경 사항 저장
            return "시간 저장 완료";
        } else {
            return "비디오를 찾을 수 없습니다.";
        }
    }
    @GetMapping("/videoInfo")
    public ResponseEntity<VideoInfo> getVideoInfo() {
        VideoInfo videoInfo = new VideoInfo(totalDuration, currentTime);
        return ResponseEntity.ok(videoInfo);
    }


    // 모든 비디오 목록 조회
    @GetMapping
    public ResponseEntity<List<VideoDTO>> getAllVideos() {
        List<VideoDTO> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    // 특정 ID의 비디오 조회
    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> getVideoById(@PathVariable Long id) {
        return videoService.getVideoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 비디오 추가
    @PostMapping
    public ResponseEntity<VideoDTO> addVideo(@RequestBody VideoDTO videoDTO) {
        VideoDTO createdVideo = videoService.addVideo(videoDTO);
        return ResponseEntity.status(201).body(createdVideo);
    }

    // 비디오 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<VideoDTO> updateVideo(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
        return videoService.updateVideo(id, videoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 비디오 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        boolean isDeleted = videoService.deleteVideo(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Getter
    @Setter
    public static class PlayTimeRequest {
        private double totalDuration;
        private double currentTime;
        private Long videoId;

    }

    public static class VideoInfo {
        private double totalDuration;
        private double currentTime;

        public VideoInfo(double totalDuration, double currentTime) {
            this.totalDuration = totalDuration;
            this.currentTime = currentTime;
        }

        public double getTotalDuration() {
            return totalDuration;
        }

        public double getCurrentTime() {
            return currentTime;
        }
    }
}
