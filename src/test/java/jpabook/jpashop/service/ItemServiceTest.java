package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @Test
    public void 상품등록() throws Exception {
        //given
        Item book = new Book();
        book.setName("JPA의 정석");
        book.setPrice(30000);
        book.setStockQuantity(10000);

        //when
        itemService.saveItem(book);

        //then
        Assertions.assertThat(book).isEqualTo(itemService.findItemById(book.getId()));
    }

    @Test
    public void 상품_재고수량_예외() throws Exception {
        //given
        Item book = new Book();
        book.setName("JPA의 정석");
        book.setPrice(30000);
        book.setStockQuantity(10000);

        //when


        //then
        assertThrows(NotEnoughStockException.class, () -> {
            book.removeStock(10001);
        });
    }
}