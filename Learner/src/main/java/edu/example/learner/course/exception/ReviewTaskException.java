package edu.example.learner.course.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class ReviewTaskException extends RuntimeException {
    private String message;
    private int code;
}
