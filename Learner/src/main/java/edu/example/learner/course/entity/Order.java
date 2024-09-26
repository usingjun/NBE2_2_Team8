package edu.example.learner.course.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Embeddable
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId ;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();

    public void changeOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }
    public void add(Course course) {
        course.changeOrder(this);
        courses.add(course);
    }

}
