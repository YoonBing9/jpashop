package jpabook.jpashop.api;

import jpabook.jpashop.api.dto.CreateMemberInDto;
import jpabook.jpashop.api.dto.CreateMemberOutDto;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
