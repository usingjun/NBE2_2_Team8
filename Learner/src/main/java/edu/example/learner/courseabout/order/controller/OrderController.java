package edu.example.learner.courseabout.order.controller;

import edu.example.learner.courseabout.order.dto.OrderDTO;
import edu.example.learner.courseabout.order.dto.OrderUpdateDTO;
import edu.example.learner.courseabout.course.service.CourseService;
import edu.example.learner.courseabout.order.service.OrderService;
import edu.example.learner.security.auth.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CourseService courseService;

    @PostMapping("/{memberId}")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long memberId) {
        log.info("Create order for member ID: " + memberId + " with order data: " + orderDTO);
        return ResponseEntity.ok(orderService.add(orderDTO, memberId));
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> readOrder(@PathVariable("orderId") Long orderId) {
        log.info("Read order: " + orderId);
        return ResponseEntity.ok(orderService.read(orderId));
    }

    @GetMapping("/list/{memberId}") // 사용자의 모든 주문 목록 가져오기
    public ResponseEntity<List<OrderDTO>> getOrders(@PathVariable Long memberId) {
        List<OrderDTO> orderDTOList = orderService.getOrdersById(memberId);
        return ResponseEntity.ok(orderDTOList);
    }


    @GetMapping("/list/admin") // 관리자 모든 Order 불러오기
    public ResponseEntity<List<OrderDTO>> readAllOrders() {
        log.info("Read all orders");
        return ResponseEntity.ok(orderService.readAll());
    }

    @PutMapping("/{orderId}/")
    public ResponseEntity<OrderUpdateDTO> updateOrder(@RequestBody OrderUpdateDTO orderUpdateDTO, @PathVariable("orderId") Long orderId) {
        log.info("Update order: " + orderUpdateDTO);
        return ResponseEntity.ok(orderService.update(orderUpdateDTO, orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        log.info("Delete order: " + orderId);
        orderService.delete(orderId);
        return ResponseEntity.ok(Map.of("success", "ok"));
    }


    // 밑에는 사용자 정보를 가져올 수 있게되면 언락 후 테스트
//    @GetMapping("/list/") // memberId를 경로에서 제거하고, AuthenticationPrincipal로 받아옴
//    public ResponseEntity<List<OrderDTO>> getOrders(@AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
//        Long memberId = userPrincipal.getId(); // 현재 로그인된 사용자의 memberId를 가져옴
//        List<OrderDTO> orderDTOList = orderService.getOrdersById(memberId);
//        return ResponseEntity.ok(orderDTOList);
//    }
//    @PostMapping("/")
//    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO, @AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
//        Long memberId = userPrincipal.getId(); // 현재 로그인된 사용자의 memberId를 가져옴
//        log.info("Create order for member ID: " + memberId + " with order data: " + orderDTO);
//        return ResponseEntity.ok(orderService.add(orderDTO, memberId));
//    }
}
