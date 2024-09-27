package edu.example.learner.inquiry.controller;

import edu.example.learner.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inquiry")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;


}
