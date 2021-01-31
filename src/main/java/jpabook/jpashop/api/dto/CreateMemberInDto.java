package jpabook.jpashop.api.dto;

import jpabook.jpashop.domain.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class CreateMemberInDto {
    @NotEmpty
    private String name;
    private Address address;
}
