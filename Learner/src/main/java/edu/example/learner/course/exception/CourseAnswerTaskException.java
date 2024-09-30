package edu.example.learner.course.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseAnswerTaskException extends RuntimeException{
    private String message;
    private int code;
}
