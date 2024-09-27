package edu.example.learner.course.service;

import edu.example.learner.course.dto.CourseDTO;
import edu.example.learner.course.dto.OrderDTO;
import edu.example.learner.course.dto.OrderItemDTO;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.Order;
import edu.example.learner.course.entity.OrderItem;
import edu.example.learner.course.repository.CourseRepository;
import edu.example.learner.course.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;

    @Override
    public OrderDTO add(OrderDTO orderDTO) {

        Order order = orderDTO.toEntity(orderDTO);
        for (OrderItemDTO dto : orderDTO.getOrderItemDTOList()) {
            Course course = courseRepository.findById(dto.getCourseId()).orElseThrow();
            log.info("course -----" + course);
            OrderItem orderItem = OrderItem.builder().course(course)
                    .price(course.getCoursePrice())
                    .build();
            order.getOrderItems().add(orderItem);
        }
        order=orderRepository.save(order);
        return orderDTO;
    }

    @Override
    public OrderDTO read(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        Order order1 = order.get();

        List<OrderItemDTO> orderItemDTOS=new ArrayList<>();
        for (OrderItem orderItem : order1.getOrderItems()) {
            orderItemDTOS.add(new OrderItemDTO(orderItem));
        }
        OrderDTO orderDTO = new OrderDTO(order1);
        orderDTO.setOrderItemDTOList(orderItemDTOS);
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
