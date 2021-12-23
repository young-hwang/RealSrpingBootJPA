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
        initService.dbInit2();
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

            Book book1 = createBook("Clean Code", 100000, 100);
            entityManager.persist(book1);

            Book book2 = createBook("TDD", 20000, 10);
            entityManager.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            entityManager.persist(order);
        }

        public void dbInit2() {
            Member member = new Member();
            member.setName("UserB");
            member.setAddress(new Address("양산", "1", "211-211"));
            entityManager.persist(member);

            Book book1 = createBook("Best Blog", 100000, 100);
            entityManager.persist(book1);

            Book book2 = createBook("TDD", 20000, 10);
            entityManager.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            entityManager.persist(order);
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }
    }
}
