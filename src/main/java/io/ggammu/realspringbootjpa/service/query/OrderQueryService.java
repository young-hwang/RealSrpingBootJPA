package io.ggammu.realspringbootjpa.service.query;

import io.ggammu.realspringbootjpa.domain.Order;
import io.ggammu.realspringbootjpa.domain.OrderItem;
import io.ggammu.realspringbootjpa.repository.OrderRepository;
import io.ggammu.realspringbootjpa.repository.OrderSearch;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<Order> findAllByString(OrderSearch orderSearch) {
        List<Order> all = orderRepository.findAllByString(orderSearch);
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

}
