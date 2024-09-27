package edu.example.learner.course.repository;

import edu.example.learner.course.dto.CourseDTO;
import edu.example.learner.course.dto.OrderDTO;
import edu.example.learner.course.dto.OrderItemDTO;
import edu.example.learner.course.entity.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Log4j2
@RequiredArgsConstructor
@Commit
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;


    @Test
    @Transactional
    @Commit
    public void add() {

        OrderDTO orderDTO = OrderDTO.builder().orderStatus(String.valueOf(OrderStatus.CANCELED))
                .build();
        Order order = orderRepository.save(orderDTO.toEntity(orderDTO));
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        OrderItemDTO build = OrderItemDTO.builder()
                .courseId(1L)
                .orderId(order.getOrderId())
                .price(10000L)
                .courseAttribute(String.valueOf(CourseAttribute.C))
                .build();
        orderItemDTOS.add(build);
        orderItemDTOS.add(build);





        for (OrderItemDTO dto : orderItemDTOS) {
            Course course = courseRepository.save(courseRepository.findById(dto.getCourseId()).orElseThrow());
            log.info("course -----" + course);
            OrderItem orderItem = orderItemRepository.save(dto.toEntity(dto));
            order.getOrderItems().add(orderItem);
        }



        System.out.println("Added Order with courses size: " + order.getOrderItems().size());
        System.out.println("Read Order with courses size: " + order.getOrderItems().get(0));
    }

    @Test
    @Transactional
    public void read() {
        Order order = orderRepository.findById(12L).orElseThrow();
        System.out.println("Read Order with courses size: " + order.getOrderItems().get(0));
    }

    @Test
    @Transactional
    @Commit
    public void update(){
        List<CourseDTO> courseDTOList = new ArrayList<>();
        courseDTOList.add(new CourseDTO(courseRepository.findById(1L).orElseThrow()));
        courseDTOList.add(new CourseDTO(courseRepository.findById(1L).orElseThrow()));
        OrderDTO orderDTO =new OrderDTO();
        orderDTO.setOrderStatus(String.valueOf(OrderStatus.FAILED));
        orderDTO.setOId(20L);
        orderDTO.setOrderItemDTOList(orderDTO.getOrderItemDTOList());
        Order order = orderRepository.findById(orderDTO.getOId()).orElseThrow();
        order.changeOrderStatus(OrderStatus.valueOf(orderDTO.getOrderStatus()));
        order.getOrderItems().clear();

        for (OrderItemDTO dto : orderDTO.getOrderItemDTOList()) {
            Course course = courseRepository.save(courseRepository.findById(dto.getCourseId()).orElseThrow());
            log.info("course -----" + course);
            OrderItem orderItem = OrderItem.builder().course(course)
                    .price(course.getCoursePrice())
                    .build();
            order.getOrderItems().add(orderItem);
        }
        Assertions.assertEquals(order.getOrderStatus(), OrderStatus.FAILED);
    }
    @Test
    @Transactional
    public void delete() {
        Long orderId = 21L;
        Order order = orderRepository.findById(orderId).orElseThrow();

    }
}
