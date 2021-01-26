package jpabook.jpashop.domain.item;

import lombok.Getter;

import javax.persistence.Entity;

@Getter
@Entity
public class Album extends Item {
    private String artist;
    private String etc;
}
