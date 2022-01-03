package io.ggammu.realspringbootjpa.repository.order.query;

import io.ggammu.realspringbootjpa.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemQueryDto {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(String itemName, int orderPrice, int count) {
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public OrderItemQueryDto(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }

}
