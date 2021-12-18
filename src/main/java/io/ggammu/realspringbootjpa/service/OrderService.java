package io.ggammu.realspringbootjpa.service;

import io.ggammu.realspringbootjpa.domain.Delivery;
import io.ggammu.realspringbootjpa.domain.Member;
import io.ggammu.realspringbootjpa.domain.Order;
import io.ggammu.realspringbootjpa.domain.OrderItem;
import io.ggammu.realspringbootjpa.domain.item.Item;
import io.ggammu.realspringbootjpa.repository.ItemRepository;
import io.ggammu.realspringbootjpa.repository.MemberRepository;
import io.ggammu.realspringbootjpa.repository.OrderRepository;
import io.ggammu.realspringbootjpa.repository.OrderSearch;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    //검색
    public List<Order> findOrdersByCriteria(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }

    public List<Order> findOrdersByString(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

    public List<Order> findOrdersByQueryDsl(OrderSearch orderSearch) {
        return orderRepository.findAllByQueryDsl(orderSearch);
    }

}
