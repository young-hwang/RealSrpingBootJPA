package io.ggammu.realspringbootjpa.api;

import io.ggammu.realspringbootjpa.domain.Address;
import io.ggammu.realspringbootjpa.domain.Order;
import io.ggammu.realspringbootjpa.domain.OrderItem;
import io.ggammu.realspringbootjpa.domain.OrderStatus;
import io.ggammu.realspringbootjpa.repository.OrderRepository;
import io.ggammu.realspringbootjpa.repository.OrderSearch;
import io.ggammu.realspringbootjpa.repository.order.query.OrderQueryDto;
import io.ggammu.realspringbootjpa.repository.order.query.OrderQueryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderv2() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> orderDtos = all.stream().map(OrderDto::new).collect(Collectors.toList());

        return orderDtos;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderv3() {
        List<Order> all = orderRepository.findAllWithItem(new OrderSearch());
        List<OrderDto> orderDtos = all.stream().map(OrderDto::new).collect(Collectors.toList());

        return orderDtos;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderv3page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> all = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> orderDtos = all.stream().map(OrderDto::new).collect(Collectors.toList());

        return orderDtos;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> orderv4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @Getter
    static class OrderDto {

        private Long id;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            this.id = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems().stream()
                            .map(OrderItemDto::new).collect(Collectors.toList());
        }

    }

    @Getter
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }

    }

}
