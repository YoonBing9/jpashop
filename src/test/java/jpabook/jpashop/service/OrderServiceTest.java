package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional(readOnly = true)
@SpringBootTest
class OrderServiceTest {
    @Autowired private EntityManager em;
    @Autowired private OrderService orderService;

    @Transactional
    @Test
    public void 주문하기() throws Exception {
        //given
        Member member = getMember();
        Item book = getItem();

        em.flush();

        //when
        int count = 100;
        Long orderId = orderService.order(member.getId(), book.getId(), count);

        //then
        Order getOrder = em.find(Order.class, orderId);
        assertEquals(orderId, getOrder.getId(), "주문 엔티티가 동일해야합니다.");
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "주문 상태여야합니다.");
        assertEquals(30000 * 100, getOrder.getTotalPrice(), "주문한 물품의 총 가격은 상품 가격 * 수량이다.");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품의 종류수가 정확해야한다.");
    }

    @Transactional
    @Test
    public void 주문재고_초과수량_주문하기() throws Exception {
        //given
        Member member = getMember();
        Item book = getItem();

        em.flush();

        //when
        int count = 10001;

        //then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), count);
        });
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = getMember();
        Item book = getItem();

        em.flush();

        int count = 100;
        Long orderId = orderService.order(member.getId(), book.getId(), count);
        Order getOrder = em.find(Order.class, orderId);

        //when
        getOrder.cancel();

        //then
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(),"주문상태가 취소가 돼야한다.");
        assertEquals(book.getStockQuantity(), 10000, "재고수량이 돌아와야한다.");
    }

    private Item getItem() {
        Item book = new Book();
        book.setName("JPA");
        book.setPrice(30000);
        book.setStockQuantity(10000);
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setName("윤빙구");
        member.setAddress(Address.builder().city("하남").street("미사강변도로").zipcode("111").build());
        em.persist(member);
        return member;
    }
}