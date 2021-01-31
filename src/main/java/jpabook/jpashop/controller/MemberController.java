package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.MemberDto;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

        Member member = Member.createMemberWithoutId(memberForm.getName(),
                Address.builder()
                        .city(memberForm.getCity())
                        .street(memberForm.getStreet())
                        .zipcode(memberForm.getZipcode()).build());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        List<MemberDto> memberDtos = new ArrayList<>();
        for (Member member : members) {
            MemberDto memberDto = new MemberDto();
            memberDto.setId(member.getId());
            memberDto.setName(member.getName());
            memberDto.setAddress(member.getAddress());
            memberDtos.add(memberDto);
        }

        model.addAttribute("members", memberDtos);

        return "members/memberList";
    }
}
