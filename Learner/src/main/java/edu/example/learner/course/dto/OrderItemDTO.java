package edu.example.learner.course.dto;

import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.CourseAttribute;
import edu.example.learner.course.entity.Order;
import edu.example.learner.course.entity.OrderItem;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Data
public class OrderItemDTO {
    private Long courseId;
    private Long orderId;
    private String courseName;
    private String courseAttribute;
    private Long price;

    private int quantity;

    public OrderItemDTO(OrderItem orderItem) {
        this.courseId = orderItem.getCourse().getCourseId();
        this.courseAttribute = String.valueOf(orderItem.getCourse().getCourseAttribute());
        this.price = orderItem.getPrice();
    }

    public OrderItem toEntity(OrderItemDTO orderItemDTO){
        return OrderItem.builder()
                .order(Order.builder().orderId(orderId).build())
                .course(Course.builder()
                        .courseId(courseId).build())
                .price(price)
                .courseAttribute(CourseAttribute.valueOf(courseAttribute))
                .build();
    }
}
