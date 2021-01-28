package jpabook.jpashop.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter @Getter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;
    
    @NotEmpty(message = "도시는 필수 입니다.")
    private String city;

    @NotEmpty(message = "거리 이름은 필수 입니다.")
    private String street;

    @NotEmpty(message = "우편번호는 필수 입니다.")
    private String zipcode;
}
