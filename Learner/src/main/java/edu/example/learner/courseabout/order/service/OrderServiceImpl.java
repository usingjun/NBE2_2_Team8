package edu.example.learner.courseabout.order.service;

import edu.example.learner.courseabout.order.dto.OrderDTO;
import edu.example.learner.courseabout.order.dto.OrderItemDTO;
import edu.example.learner.courseabout.order.dto.OrderUpdateDTO;
import edu.example.learner.courseabout.order.exception.OrderException;
import edu.example.learner.courseabout.order.repository.OrderItemRepository;
import edu.example.learner.courseabout.order.repository.OrderRepository;

import edu.example.learner.courseabout.course.entity.Course;
import edu.example.learner.courseabout.order.entity.Order;
import edu.example.learner.courseabout.order.entity.OrderItem;
import edu.example.learner.courseabout.order.entity.OrderStatus;

import edu.example.learner.courseabout.course.repository.CourseRepository;
import edu.example.learner.member.entity.Member;
import edu.example.learner.member.exception.MemberException;
import edu.example.learner.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Override
    public OrderDTO add(OrderDTO orderDTO,Long memberId) {
        orderDTO.setMemberId(memberId);
        Order order = orderDTO.toEntity(orderDTO); // Order DTO를 Order로 변환
        order = orderRepository.save(order); // Order 저장
        double totalPrice = 0.0;

        // orderDTO안에 orderItemDTOList를 orderItemList로 변환
        // DTO안에 있는 강의 번호를 조회해 orderItemlist
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOList()) {
            log.debug("CourseAttribute value {}", orderItemDTO.getCourseAttribute());
            Course course = courseRepository.findById(orderItemDTO.getCourseId()).orElseThrow();
            orderItemDTO.setPrice(course.getCoursePrice());
            orderItemDTO.setCourseAttribute(String.valueOf(course.getCourseAttribute()));
            OrderItem orderItem = orderItemRepository.save(orderItemDTO.toEntity(orderItemDTO,order));
            order.getOrderItems().add(orderItem);
            totalPrice += orderItem.getPrice();
        }
        order.changeTotalPrice(totalPrice);
        order = orderRepository.save(order);
        orderDTO.setTotalPrice(totalPrice);
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
    public List<OrderDTO> getOrdersById(Long memberId) {
        Member member = memberRepository.getMemberInfo(memberId);
        List<Order> orders = orderRepository.findByMember(member);
        List<OrderDTO> orderDTOListByMember = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTO.setOrderItemDTOList(new ArrayList<>()); // 리스트 초기화

            for (OrderItem orderItem : order.getOrderItems()) {
                orderDTO.getOrderItemDTOList().add(new OrderItemDTO(orderItem));
            }
            orderDTOListByMember.add(orderDTO);
        }
        return orderDTOListByMember;
    }


    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
