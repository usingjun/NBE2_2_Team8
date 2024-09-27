package edu.example.learner.course.service;

import edu.example.learner.course.dto.OrderDTO;
import edu.example.learner.course.dto.OrderUpdateDTO;

import java.util.List;

public interface OrderService {
    OrderDTO add(OrderDTO orderDTO);
    OrderDTO read(Long orderId);
    OrderUpdateDTO update(OrderUpdateDTO orderUpdateDTODTO, Long orderId);

    void delete(Long courseId);
    List<OrderDTO> readAll();
    void deleteAll();
}
