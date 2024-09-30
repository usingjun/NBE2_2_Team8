package edu.example.learner.course.controller.video;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VideoController {

    @GetMapping("/video/{id}")
    public String getVideo(@PathVariable Long id, Model model) {
        // YouTube URL을 가져오는 로직
        String videoUrl = "https://www.youtube.com/embed/ItDjh4twvm8"; // 예시 URL
        model.addAttribute("videoUrl", videoUrl);
        return "video"; // Thymeleaf 템플릿 이름
    }
}

