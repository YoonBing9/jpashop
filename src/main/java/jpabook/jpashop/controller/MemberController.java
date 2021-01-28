package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(Address.builder()
                .city(memberForm.getCity())
                .street(memberForm.getStreet())
                .zipcode(memberForm.getZipcode()).build());

        memberService.join(member);

        return "redirect:/";
    }
}
