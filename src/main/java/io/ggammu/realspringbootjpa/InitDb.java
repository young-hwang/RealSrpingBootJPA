package io.ggammu.realspringbootjpa;

import io.ggammu.realspringbootjpa.domain.Address;
import io.ggammu.realspringbootjpa.domain.Delivery;
import io.ggammu.realspringbootjpa.domain.Member;
import io.ggammu.realspringbootjpa.domain.Order;
import io.ggammu.realspringbootjpa.domain.OrderItem;
import io.ggammu.realspringbootjpa.domain.item.Book;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @RequiredArgsConstructor
    @Transactional
    @Component
    static class InitService {

        private final EntityManager entityManager;

        public void dbInit() {
            Member member = new Member();
            member.setName("UserA");
            member.setAddress(new Address("부산", "1", "111-111"));
            entityManager.persist(member);

            Book book1 = new Book();
            book1.setName("Clean Code");
            book1.setPrice(100000);
            book1.setStockQuantity(100);
            entityManager.persist(book1);

            Book book2 = new Book();
            book2.setName("TDD");
            book2.setPrice(20000);
            book2.setStockQuantity(10);
            entityManager.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            entityManager.persist(order);
        }

    }
}
