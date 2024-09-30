package edu.example.learner.courseabout.order.service;

import edu.example.learner.courseabout.order.dto.OrderDTO;
import edu.example.learner.courseabout.order.dto.OrderUpdateDTO;

import java.util.List;

public interface OrderService {
    OrderDTO add(OrderDTO orderDTO);
    OrderDTO read(Long orderId);
    OrderUpdateDTO update(OrderUpdateDTO orderUpdateDTODTO, Long orderId);

    void delete(Long courseId);
    List<OrderDTO> readAll();
    void deleteAll();
}
