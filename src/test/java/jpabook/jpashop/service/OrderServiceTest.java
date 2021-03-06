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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class OrderServiceTest {
    @Autowired private EntityManager em;
    @Autowired private OrderService orderService;

    @Test
    public void 주문하기() throws Exception {
        //given
        Member member = getMember();
        Item book = getItem();

        em.flush();

        //when
        int count = 100;
        System.out.println("start order");
        Long orderId = orderService.order(member.getId(), book.getId(), count);
        System.out.println("end order");

        System.out.println("orderId: "+orderId);

        //then
        Order getOrder = em.find(Order.class, orderId);
        System.out.println("getOrder: "+getOrder);
        assertEquals(orderId, getOrder.getId(), "주문 엔티티가 동일해야합니다.");
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "주문 상태여야합니다.");
        assertEquals(30000 * 100, getOrder.getTotalPrice(), "주문한 물품의 총 가격은 상품 가격 * 수량이다.");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품의 종류수가 정확해야한다.");
    }

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
        Item book = Book.createBook("JPA", 30000, 10000, "윤빙구", "ISBN");
        em.persist(book);
        return book;
    }

    private Member getMember() {
        String name = "윤빙구";
        String city = "경기도";
        String street = "미사강변도로";
        String zipcode = "111";
        Member member = Member.createMemberWithoutId(name, Address.builder().city(city).street(street).zipcode(zipcode).build());

        em.persist(member);
        return member;
    }
}