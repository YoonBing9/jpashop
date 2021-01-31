package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @Test
    public void 상품등록() throws Exception {
        //given

        //when
        Long bookId = itemService.saveBook("JPA의 정석", 30000, 10000, "윤빙구", "");
        Book book = (Book)itemService.findItemById(bookId);

        //then
        assertEquals("JPA의 정석", book.getName(), "상품명이 정상 등록되야합니다.");
        assertEquals(30000, book.getPrice(), "상품 가격이 정상 등록되야합니다.");
        assertEquals(10000, book.getStockQuantity(), "상품 재고 수량이 정상 등록되야합니다.");
        assertEquals("윤빙구", book.getAuthor(), "상품 저자가 정상 등록되야합니다.");
        assertEquals("", book.getIsbn(), "상품 ISBN이 정상 등록되야합니다.");
    }

    @Test
    public void 상품_재고수량_예외() throws Exception {
        //given

        //when
        Long bookId = itemService.saveBook("JPA의 정석", 30000, 10000, "윤빙구", "");
        Item book = itemService.findItemById(bookId);

        //then
        assertThrows(NotEnoughStockException.class, () -> {
            book.removeStock(10001);
        });
    }
}