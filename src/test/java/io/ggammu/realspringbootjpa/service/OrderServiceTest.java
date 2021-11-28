package io.ggammu.realspringbootjpa.service;

import io.ggammu.realspringbootjpa.domain.Address;
import io.ggammu.realspringbootjpa.domain.Member;
import io.ggammu.realspringbootjpa.domain.Order;
import io.ggammu.realspringbootjpa.domain.OrderStatus;
import io.ggammu.realspringbootjpa.domain.item.Book;
import io.ggammu.realspringbootjpa.exception.NotEnoughStockException;
import io.ggammu.realspringbootjpa.repository.OrderRepository;
import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long order = orderService.order(member.getId(), book.getId(), orderCount);
        Order getOrder = orderRepository.findOne(order);

        //then
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000 * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    private Book createBook(String jpa, int price, int quantity) {
        Book book = new Book();
        book.setName(jpa);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("부산", "경남", "123-123"));
        em.persist(member);
        return member;
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);
        int orderCount = 11;

        //when
        Throwable throwable = catchThrowable(() -> orderService.order(member.getId(), book.getId(), orderCount));

        //then
        assertThat(throwable).isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long order = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(order);

        //then
        Order getOrder = orderRepository.findOne(order);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }
}