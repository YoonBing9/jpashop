package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Entity
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Setter
    private String name;

    @Setter
    private int price;

    @Setter
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories;

    /**
     * 재고수량 늘리기
     */
    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

    /**
     * 재고수량 줄이기
     */
    public void removeStock(int quantity) {
        int resultStock = stockQuantity - quantity;
        if(resultStock < 0) {
            throw new NotEnoughStockException("남은 재고가 충분하지 않습니다.");
        }

        stockQuantity = resultStock;
    }
}
