package edu.example.learner.course.dto;

import edu.example.learner.course.entity.Order;
import edu.example.learner.course.entity.OrderStatus;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private Long oId;
    private List<CourseDTO> courseDTOList;
    private String orderStatus;

    public OrderDTO(Order order) {
        this.oId=order.getOrderId();
        this.orderStatus= String.valueOf(order.getOrderStatus());
    }

    public Order toEntity(OrderDTO orderDTO) {
        return Order.builder()
                .orderId(orderDTO.getOId())
                .orderStatus(OrderStatus.valueOf(orderDTO.getOrderStatus()))
                .build();
    }
}
