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
        Member member = new Member();
        member.setName("윤빙구");
        member.setAddress(Address.builder().city("경기도").street("미사강변도로").zipcode("111").build());

        //when
        memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberService.findOne(member.getId()));
    }

    @Test
    public void 회원가입_중복_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("윤빙구");

        Member member2 = new Member();
        member2.setName("윤빙구");

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
}