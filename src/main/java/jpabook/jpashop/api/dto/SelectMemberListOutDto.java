package jpabook.jpashop.api.dto;

import jpabook.jpashop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class SelectMemberListOutDto {
    private String name;
    private Address address;
}
