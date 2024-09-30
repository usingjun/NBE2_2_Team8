package edu.example.learner.course.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CourseTaskException extends RuntimeException {
    private String message;
    private int code;
    public CourseTaskException(String message) {
        super(message);
    }
}
