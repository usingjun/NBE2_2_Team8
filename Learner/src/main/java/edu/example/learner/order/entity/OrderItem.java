package edu.example.learner.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.CourseAttribute;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Embeddable
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"order"})
@Table(name = "order_Item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "orderId")
    private Order order;

    private CourseAttribute courseAttribute;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Long price;

}
