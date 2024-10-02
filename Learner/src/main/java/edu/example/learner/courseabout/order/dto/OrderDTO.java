package edu.example.learner.courseabout.order.dto;

import edu.example.learner.courseabout.order.entity.Order;
import edu.example.learner.courseabout.order.entity.OrderStatus;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private Long oId;
    private List<OrderItemDTO> orderItemDTOList;
    private String orderStatus;
    private String createdDate;
    private Double totalPrice;

    public OrderDTO(Order order) {
        this.oId = order.getOrderId();
        this.orderStatus = String.valueOf(order.getOrderStatus());
        this.createdDate = order.getCreatedAt() != null ? order.getCreatedAt().toString() : null; // 날짜 형식 변환
        this.totalPrice = order.getTotalPrice(); // 총 금액 설정
    }

    public Order toEntity(OrderDTO orderDTO) {
        return Order.builder()
                .orderId(orderDTO.getOId())
                .orderStatus(OrderStatus.valueOf(orderDTO.getOrderStatus()))
                .build();
    }
}
