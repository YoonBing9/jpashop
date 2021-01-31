package jpabook.jpashop.api.dto;

import jpabook.jpashop.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateMemberInDto {
    private String name;
    private Address address;
}
