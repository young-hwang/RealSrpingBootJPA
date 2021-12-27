package io.ggammu.realspringbootjpa.repository.order.simplequery;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findAllOrderDto() {
        return em.createQuery(
                        "select new io.ggammu.realspringbootjpa.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o " +
                                "join o.member m " +
                                "join o.delivery d ", OrderSimpleQueryDto.class)
                .getResultList();
    }
}
