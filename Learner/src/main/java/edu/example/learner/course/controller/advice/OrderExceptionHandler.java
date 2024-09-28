package edu.example.learner.course.controller.advice;

import edu.example.learner.course.exception.OrderTaskException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@Log4j2
public class OrderExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleOrderException(OrderTaskException e){
        log.error("OrderTaskException : ", e);
        Map<String, Object> map = Map.of("error", e.getMessage());

        return ResponseEntity.status(e.getCode()).body(map);
    }
}
