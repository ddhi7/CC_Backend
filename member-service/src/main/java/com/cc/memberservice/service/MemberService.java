package com.cc.memberservice.service;

import com.cc.memberservice.dto.MemberResponse;
import com.cc.memberservice.entity.Member;
import com.cc.memberservice.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        return MemberResponse.from(member);
    }

    public MemberResponse getByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        return MemberResponse.from(member);
    }
}
