package com.cc.memberservice.controller;

import com.cc.memberservice.dto.MemberResponse;
import com.cc.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/email/{email}")
    public ResponseEntity<MemberResponse> findByEmail(@PathVariable("email") String email) {
        MemberResponse dto = memberService.getByEmail(email);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(memberService.getById(id));
    }
}
