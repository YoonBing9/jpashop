package jpabook.jpashop.api;

import jpabook.jpashop.api.dto.CreateMemberInDto;
import jpabook.jpashop.api.dto.CreateMemberOutDto;
import jpabook.jpashop.api.dto.UpdateMemberInDto;
import jpabook.jpashop.api.dto.UpdateMemberOutDto;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("member/join")
    public ResponseEntity<CreateMemberOutDto> join(@RequestBody @Valid CreateMemberInDto createMemberInDto, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Long memberId = memberService.join(Member.createMemberWithoutId(createMemberInDto.getName(), createMemberInDto.getAddress()));
        CreateMemberOutDto createMemberOutDto = new CreateMemberOutDto();
        createMemberOutDto.setId(memberId);

        return ResponseEntity.ok().body(createMemberOutDto);
    }

    @PatchMapping("member/{id}")
    public ResponseEntity<UpdateMemberOutDto> update(@PathVariable("id")Long id, @RequestBody @Valid UpdateMemberInDto updateMemberInDto, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Long memberId = memberService.modifyMemberInfo(id, updateMemberInDto.getName(), updateMemberInDto.getAddress());
        Member member = memberService.findOne(memberId);

        UpdateMemberOutDto updateMemberOutDto = new UpdateMemberOutDto();
        updateMemberOutDto.setId(member.getId());
        updateMemberOutDto.setName(member.getName());

        return ResponseEntity.ok().body(updateMemberOutDto);
    }
}
