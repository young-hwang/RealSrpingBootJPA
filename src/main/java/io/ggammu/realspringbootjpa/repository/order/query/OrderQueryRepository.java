package io.ggammu.realspringbootjpa.repository.order.query;

import io.ggammu.realspringbootjpa.domain.Order;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> orders = findOrders();

        orders.forEach(o -> {
            List<OrderItemQueryDto> orderItem = findOrderItem(o.getId());
            o.setOrderItems(orderItem);
        });

        return orders;
    }

    private List<OrderItemQueryDto> findOrderItem(Long id) {
        return em.createQuery(
                "select new io.ggammu.realspringbootjpa.repository.order.query.OrderItemQueryDto(i.name, oi.orderPrice, oi.count) " +
//                        "select new io.ggammu.realspringbootjpa.repository.order.query.OrderItemQueryDto(oi) " +
                        "from OrderItem oi " +
                        "join oi.item i " +
                        "where oi.order.id = :id",
                OrderItemQueryDto.class
        )
                .setParameter("id", id)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new io.ggammu.realspringbootjpa.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o " +
                        "join o.member m " +
                        "join o.delivery d ", OrderQueryDto.class).getResultList();
    }

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> orders = findOrders();

        List<Long> orderIds = orders.stream().map(o -> o.getId()).collect(Collectors.toList());

        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new io.ggammu.realspringbootjpa.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
//                        "select new io.ggammu.realspringbootjpa.repository.order.query.OrderItemQueryDto(oi) " +
                                "from OrderItem oi " +
                                "join oi.item i " +
                                "where oi.order.id in :ids",
                        OrderItemQueryDto.class
                )
                .setParameter("ids", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemsMap= orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));

        orders.forEach(o -> o.setOrderItems(orderItemsMap.get(o.getId())));

        return orders;
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        List<OrderFlatDto> resultList = em.createQuery(
                "select " +
                        " new io.ggammu.realspringbootjpa.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d " +
                        "join o.orderItems oi " +
                        "join oi.item i ",
                OrderFlatDto.class)
                .getResultList();
        return resultList;
    }

}
