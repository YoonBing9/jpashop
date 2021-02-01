package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Component
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.doInit1();
        initService.doInit2();
    }

    @RequiredArgsConstructor
    @Transactional
    @Component
    static class InitService {
        private final EntityManager em;

        public void doInit1() {
            Member member = Member.createMemberWithoutId("윤병현", Address.builder().city("하남").street("미사강변도로").zipcode("123").build());
            em.persist(member);

            Item book1 = Book.createBook("JPA1", 10000, 100, "김영한", "4444");
            em.persist(book1);

            Item book2 = Book.createBook("JPA2", 20000, 100, "김영한", "5555");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);

            Delivery delivery = Delivery.createDelivery(member.getAddress(), DeliveryStatus.READY);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void doInit2() {
            Member member = Member.createMemberWithoutId("윤빙구", Address.builder().city("성남").street("성남도로").zipcode("456").build());
            em.persist(member);

            Item book1 = Book.createBook("SPRING1", 15000, 200, "김영한", "6666");
            em.persist(book1);

            Item book2 = Book.createBook("SPRING2", 25000, 200, "김영한", "7777");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 4);

            Delivery delivery = Delivery.createDelivery(member.getAddress(), DeliveryStatus.READY);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
