package edu.example.learner.course.repository;

import edu.example.learner.course.dto.CourseDTO;
import edu.example.learner.course.dto.OrderDTO;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.Order;
import edu.example.learner.course.entity.OrderStatus;
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
import java.util.Optional;

@SpringBootTest
@Log4j2
@RequiredArgsConstructor
@Commit
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CourseRepository courseRepository;


    @Test
    @Transactional
    @Commit
    public void add() {
        List<CourseDTO> courseDTOList = new ArrayList<>();
        CourseDTO courseDTO = new CourseDTO(courseRepository.findById(1L).orElseThrow());
        courseDTOList.add(courseDTO);
        courseDTOList.add(new CourseDTO(courseRepository.findById(2L).orElseThrow()));
        OrderDTO orderDTO = OrderDTO.builder().orderStatus(String.valueOf(OrderStatus.ACCEPT))
                .courseDTOList(courseDTOList).build();

        Order order = orderDTO.toEntity(orderDTO);
        for (CourseDTO dto : orderDTO.getCourseDTOList()) {
            order.getCourses().add(courseRepository.findById(dto.getCourseId()).orElseThrow());
        }
        orderRepository.save(order);
        System.out.println("Added Order with courses size: " + order.getCourses().size());
        System.out.println("Read Order with courses size: " + order.getCourses().get(0));
    }

    @Test
    @Transactional
    public void read() {
        Order order = orderRepository.findById(1L).orElseThrow();
        System.out.println("Read Order with courses size: " + order.getCourses().get(0));
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
        orderDTO.setCourseDTOList(courseDTOList);
        Order order = orderRepository.findById(orderDTO.getOId()).orElseThrow();
        order.changeOrderStatus(OrderStatus.valueOf(orderDTO.getOrderStatus()));
        order.getCourses().clear();

        for (CourseDTO courseDTO : courseDTOList) {
            order.getCourses().add(courseRepository.findById(courseDTO.getCourseId()).orElseThrow());
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
