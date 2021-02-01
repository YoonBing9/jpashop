package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        //given
        String name = "유니크";
        String city = "경기도";
        String street = "미사강변도로";
        String zipcode = "111";
        Member member = Member.createMemberWithoutId(name, Address.builder().city(city).street(street).zipcode(zipcode).build());

        //when
        memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberService.findOne(member.getId()));
    }

    @Test
    public void 회원가입_중복_예외() throws Exception {
        //given
        String name = "유니크";
        Member member1 = Member.createMemberWithoutId(name, null);
        Member member2 = Member.createMemberWithoutId(name, null);

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
}