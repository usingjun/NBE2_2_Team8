package edu.example.learner.course.service;

import edu.example.learner.course.dto.OrderDTO;
import edu.example.learner.course.dto.OrderItemDTO;
import edu.example.learner.course.dto.OrderUpdateDTO;
import edu.example.learner.course.entity.Course;
import edu.example.learner.course.entity.Order;
import edu.example.learner.course.entity.OrderItem;
import edu.example.learner.course.entity.OrderStatus;
import edu.example.learner.course.exception.OrderException;
import edu.example.learner.course.repository.CourseRepository;
import edu.example.learner.course.repository.OrderItemRepository;
import edu.example.learner.course.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderDTO add(OrderDTO orderDTO) {
        Order order = orderDTO.toEntity(orderDTO); // Order DTO를 Order로 변환
        order = orderRepository.save(order); // Order 저장

        // orderDTO안에 orderItemDTOList를 orderItemList로 변환
        // DTO안에 있는 강의 번호를 조회해 orderItemlist
        for (OrderItemDTO dto : orderDTO.getOrderItemDTOList()) {
            log.debug("CourseAttribute value {}", dto.getCourseAttribute());
            Course course = courseRepository.findById(dto.getCourseId()).orElseThrow();
            dto.setCourseAttribute(String.valueOf(course.getCourseAttribute()));
            OrderItem orderItem = orderItemRepository.save(dto.toEntity(dto,order));
            order.getOrderItems().add(orderItem);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO read(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderException.ORDER_NOT_FOUND::get);

        List<OrderItemDTO> orderItemDTOS=new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemDTO orderItemDTO=new OrderItemDTO();
            orderItemDTO.setPrice(orderItem.getCourse().getCoursePrice());
            orderItemDTOS.add(new OrderItemDTO(orderItem));
        }

        OrderDTO orderDTO = new OrderDTO(order);
        orderDTO.setOrderItemDTOList(orderItemDTOS);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderUpdateDTO update(OrderUpdateDTO orderUpdateDTO, Long orderId) {
        Order foundOrder = orderRepository.findById(orderId).orElseThrow(OrderException.ORDER_NOT_FOUND::get);
        List<OrderItem> orderItems = new ArrayList<>();


        foundOrder.changeOrderStatus(OrderStatus.valueOf(orderUpdateDTO.getOrderStatus()));

        // orderDTO안에 orderItemDTOList를 orderItemList로 변환
        // DTO안에 있는 강의 번호를 조회해 orderItemlist
        foundOrder.getOrderItems().clear();
        for (OrderItemDTO dto : orderUpdateDTO.getOrderItemDTOList()) {
            Course course = courseRepository.findById(dto.getCourseId()).orElseThrow();
            dto.setCourseAttribute(String.valueOf(course.getCourseAttribute()));
            OrderItem orderItem = orderItemRepository.save(dto.toEntity(dto,foundOrder));

            foundOrder.getOrderItems().add(orderItem);
        }

        return new OrderUpdateDTO(foundOrder); // 또는 foundOrder를 기반으로 새로운 DTO를 생성해 반환
    }


    @Override
    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }

//    @Override
//    public List<OrderDTO> readAll() {
//        List<Order> orders = orderRepository.findAll();
//        List<OrderDTO> orderDTOList = new ArrayList<>();
//        for (int i = 0; i <orders.size(); i++) {
//            orderDTOList.add(new OrderDTO(orders.get(i)));
//            for (OrderItem orderItem : orders.get(i).getOrderItems()) {
//                orderDTOList.get(i).getOrderItemDTOList().add(new OrderItemDTO(orderItem));
//            }
//        }
//        return orderDTOList;
//    }

    @Override
    public List<OrderDTO> readAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTO.setOrderItemDTOList(new ArrayList<>()); // 리스트 초기화

            for (OrderItem orderItem : order.getOrderItems()) {
                orderDTO.getOrderItemDTOList().add(new OrderItemDTO(orderItem));
            }
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
