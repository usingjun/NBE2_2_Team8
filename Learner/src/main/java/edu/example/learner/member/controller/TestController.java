package edu.example.learner.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/my")
    public String my() {
        return "my";
    }
}
