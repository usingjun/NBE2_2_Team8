package edu.example.learner.courseabout.order.repository;

import edu.example.learner.courseabout.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
