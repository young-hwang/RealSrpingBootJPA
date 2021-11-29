package io.ggammu.realspringbootjpa.repository;

import io.ggammu.realspringbootjpa.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus;

}
