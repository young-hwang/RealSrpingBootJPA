package io.ggammu.realspringbootjpa.service;

import io.ggammu.realspringbootjpa.domain.Member;
import io.ggammu.realspringbootjpa.domain.item.Item;
import io.ggammu.realspringbootjpa.repository.ItemRepository;
import io.ggammu.realspringbootjpa.repository.MemberRepository;
import io.ggammu.realspringbootjpa.repository.OrderRepository;
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
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성

        //주문상품 생성

    }

    //취소

    //검색

}
