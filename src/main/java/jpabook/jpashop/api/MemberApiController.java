package jpabook.jpashop.api;

import jpabook.jpashop.api.dto.*;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("api/members/join")
    public ResponseEntity<CreateMemberOutDto> join(@RequestBody @Valid CreateMemberInDto createMemberInDto, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Long memberId = memberService.join(Member.createMemberWithoutId(createMemberInDto.getName(), createMemberInDto.getAddress()));
        CreateMemberOutDto createMemberOutDto = new CreateMemberOutDto();
        createMemberOutDto.setId(memberId);

        return ResponseEntity.ok().body(createMemberOutDto);
    }

    @PatchMapping("api/members/{id}")
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

    @GetMapping("api/members")
    public ResponseEntity selectAll() {
        List<Member> members = memberService.findMembers();
        List<SelectMemberListOutDto> outDto = members.stream()
                .map(member -> new SelectMemberListOutDto(member.getName(), member.getAddress()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new Result(outDto.size(), outDto));
    }

    @AllArgsConstructor
    @Data
    private class Result<T> {
        private int count;
        private T data;
    }
}
