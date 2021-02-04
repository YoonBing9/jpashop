package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.dto.OrderResDto;
import jpabook.jpashop.repository.query.OrderQueryReository;
import jpabook.jpashop.repository.query.dto.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderQueryReository orderQueryReository;

    /**
     * 주문하기
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //주문에 상품 등록
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //배달 정보 생성
        Delivery delivery = Delivery.createDelivery(member.getAddress(), DeliveryStatus.READY);

        //주문 정보 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소하기
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    /**
     * 주문 검색하기
     */
    public List<Order> search(OrderSearch orderSearch) {
        return orderRepository.findByCriteria(orderSearch);
    }

    /**
     * 주문리스트 조회(DTO조회)
     */
    public List<OrderQueryDto> getOrderDtoList() {
        return orderQueryReository.findAllToDto();
    }

    /**
     * 주문리스트 조회(페이징)
     */
    public List<OrderResDto> getOrderList(int offset, int limit) {
        return orderRepository.findWithPaging(offset,limit).stream()
                .map(OrderResDto::new)
                .collect(toList());
    }
}
