package edu.example.learner.courseabout.video.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class VideoController {

    private double totalDuration = 0; // 전체 동영상 시간
    private double currentTime = 0; // 현재 재생 시간

    @PostMapping("/savePlayTime")
    public String savePlayTime(@RequestBody PlayTimeRequest playTimeRequest) {
        if (playTimeRequest.getTotalDuration() > 0) {
            totalDuration = playTimeRequest.getTotalDuration(); // 전체 동영상 시간 업데이트
            System.out.println("전체 동영상 시간: " + totalDuration + "초");
        } else {
            currentTime = playTimeRequest.getCurrentTime(); // 현재 재생 시간 업데이트
            System.out.println("현재 동영상 재생 시간: " + currentTime + "초");
        }
        return "시간 저장 완료";
    }

    @GetMapping("/videoInfo")
    public VideoInfo getVideoInfo() {
        return new VideoInfo(totalDuration, currentTime);
    }

    public static class PlayTimeRequest {
        private double totalDuration;
        private double currentTime;

        public double getTotalDuration() {
            return totalDuration;
        }

        public void setTotalDuration(double totalDuration) {
            this.totalDuration = totalDuration;
        }

        public double getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(double currentTime) {
            this.currentTime = currentTime;
        }
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
