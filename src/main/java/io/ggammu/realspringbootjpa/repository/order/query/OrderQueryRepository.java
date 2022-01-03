package io.ggammu.realspringbootjpa.repository.order.query;

import io.ggammu.realspringbootjpa.domain.Order;
import java.util.List;
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

}
