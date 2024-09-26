package edu.example.learner.course.service;

import edu.example.learner.course.dto.CourseDTO;
import edu.example.learner.course.dto.OrderDTO;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.Order;
import edu.example.learner.course.repository.CourseRepository;
import edu.example.learner.course.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;

    @Override
    public OrderDTO add(OrderDTO orderDTO) {
        Order order = orderDTO.toEntity(orderDTO);
        order=orderRepository.save(order);
        for (CourseDTO courseDTO : orderDTO.getCourseDTOList()) {
            Optional<Course> foundCourse = courseRepository.findById(courseDTO.getCourseId());
            Course course = foundCourse.get();//orElse Exception 추가 후
            order.add(course);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO read(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        Order order1 = order.get();
        List<CourseDTO> courseDTOList=new ArrayList<>();
        for (Course cours : order1.getCourses()) {
            courseDTOList.add(new CourseDTO(cours));
        }
        OrderDTO orderDTO = new OrderDTO(order1);
        orderDTO.setCourseDTOList(courseDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        Order order = orderDTO.toEntity(orderDTO);
        return orderDTO;
    }

    @Override
    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDTO> readAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            orderDTOList.add(new OrderDTO(order));
        }
        return orderDTOList;
    }
}
