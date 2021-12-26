package io.ggammu.realspringbootjpa.api;

import io.ggammu.realspringbootjpa.domain.Address;
import io.ggammu.realspringbootjpa.domain.Order;
import io.ggammu.realspringbootjpa.domain.OrderStatus;
import io.ggammu.realspringbootjpa.repository.OrderRepository;
import io.ggammu.realspringbootjpa.repository.OrderSearch;
import io.ggammu.realspringbootjpa.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderService orderService;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderService.findOrdersByString(new OrderSearch());
        for (Order order: all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        List<Order> orders= orderService.findOrdersByString(new OrderSearch());
        List<SimpleOrderDto> simpleOrderDtos = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
        return simpleOrderDtos;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> orders = orderService.findAllWithMemberDelivery();
        List<SimpleOrderDto> simpleOrderDtos = orders.stream().map(SimpleOrderDto::new).collect(toList());
        return simpleOrderDtos;
    }

    @Data
    private class SimpleOrderDto {
        private Long id;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            id = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
