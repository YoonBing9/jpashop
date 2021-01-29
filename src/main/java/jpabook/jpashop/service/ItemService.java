package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(Long id) {
        return itemRepository.findOne(id);
    }

    @Transactional
    public void changeItemInfo(Long id, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(id);
        item.changeItemInfo(name, price, stockQuantity);
    }

    /**
     * 현 프로젝트는 Item이 Book에 한하여 진행함.
     */
    @Transactional
    public Long saveBook(String name, int price, int stockQuantity, String author, String isbn) {
        Item book = Book.createBook(name, price, stockQuantity, author, isbn);
        itemRepository.save(book);
        return book.getId();
    }
}
