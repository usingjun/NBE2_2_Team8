package edu.example.learner.course.dto;

import edu.example.learner.course.entity.Order;
import edu.example.learner.course.entity.OrderStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderUpdateDTO {
    private Long oId; // 필수 필드
    private String orderStatus; // 수정할 수 있는 필드
    private List<OrderItemDTO> orderItemDTOList; // 선택적, 필요 시 업데이트 가능

    public OrderUpdateDTO(Order order) {
        this.oId = order.getOrderId();
        this.orderStatus = order.getOrderStatus().toString();
        this.orderItemDTOList = new ArrayList<>();
        order.getOrderItems().forEach(item -> {
            this.orderItemDTOList.add(new OrderItemDTO(item));
        });
    }

    // OrderUpdateDTO를 Order 엔티티로 변환하는 메서드
    public Order toEntity() {
        return Order.builder()
                .orderId(this.oId)
                .orderStatus(orderStatus != null ? OrderStatus.valueOf(orderStatus) : null)
                .build();
    }


}
