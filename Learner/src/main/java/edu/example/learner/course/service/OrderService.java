package edu.example.learner.course.service;

import edu.example.learner.course.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO add(OrderDTO orderDTO);
    OrderDTO read(Long orderId);
    OrderDTO update(OrderDTO orderDTO);
    void delete(Long courseId);
    List<OrderDTO> readAll();
}
