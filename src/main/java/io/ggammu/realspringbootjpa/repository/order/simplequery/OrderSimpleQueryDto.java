package io.ggammu.realspringbootjpa.repository.order.simplequery;

import io.ggammu.realspringbootjpa.domain.Address;
import io.ggammu.realspringbootjpa.domain.Order;
import io.ggammu.realspringbootjpa.domain.OrderStatus;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderSimpleQueryDto {
    private Long id;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address) {
        this.id = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = status;
        this.address = address;
    }
}
