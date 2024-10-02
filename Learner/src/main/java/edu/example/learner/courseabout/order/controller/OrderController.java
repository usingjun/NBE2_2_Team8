package edu.example.learner.courseabout.order.controller;

import edu.example.learner.courseabout.order.dto.OrderDTO;
import edu.example.learner.courseabout.order.dto.OrderUpdateDTO;
import edu.example.learner.courseabout.course.service.CourseService;
import edu.example.learner.courseabout.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("Create order: " + orderDTO);
        return ResponseEntity.ok(orderService.add(orderDTO));
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> readOrder(@PathVariable("orderId") Long orderId) {
        log.info("Read order: " + orderId);
        return ResponseEntity.ok(orderService.read(orderId));
    }
    @GetMapping("/list")
    public ResponseEntity<List<OrderDTO>> readAllOrders() {
        log.info("Read all orders");
        return ResponseEntity.ok(orderService.readAll());
    }
    @PutMapping("/{orderId}/")
    public ResponseEntity<OrderUpdateDTO> updateOrder(@RequestBody OrderUpdateDTO orderUpdateDTO, @PathVariable("orderId") Long orderId) {
        log.info("Update order: " + orderUpdateDTO);
        return ResponseEntity.ok(orderService.update(orderUpdateDTO,orderId));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestParam Long orderId) {
        log.info("Delete order: " + orderId);
        orderService.delete(orderId);
        return ResponseEntity.ok(Map.of("succese","ok"));
    }


}
