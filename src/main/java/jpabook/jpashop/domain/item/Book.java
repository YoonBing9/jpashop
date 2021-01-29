package jpabook.jpashop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Book extends Item {
    private String author;
    private String isbn;

    /**
     * 생성 메서드
     */
    public static Item createItem(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;

        return this;
    }

    /**
     * 수정 메서드
     * */
    public void changeItemInfo(String name, int price, int stockQuantity, String author, String isbn) {
        super.changeItemInfo(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }
}
