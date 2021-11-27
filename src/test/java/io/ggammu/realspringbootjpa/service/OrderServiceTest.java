package io.ggammu.realspringbootjpa.service;

import io.ggammu.realspringbootjpa.domain.Address;
import io.ggammu.realspringbootjpa.domain.Member;
import io.ggammu.realspringbootjpa.domain.Order;
import io.ggammu.realspringbootjpa.domain.OrderStatus;
import io.ggammu.realspringbootjpa.domain.item.Book;
import io.ggammu.realspringbootjpa.repository.OrderRepository;
import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;
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
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("부산", "경남", "123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        //when
        Long order = orderService.order(member.getId(), book.getId(), orderCount);
        Order getOrder = orderRepository.findOne(order);

        //then
        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
    }

}