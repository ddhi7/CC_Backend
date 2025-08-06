package com.cc.memberservice.dto;


import com.cc.memberservice.entity.Member;
import com.cc.memberservice.entity.enums.ActiveStatus;
import com.cc.memberservice.entity.enums.Role;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class MemberResponse {
    private Long   id;
    private String username;
    private String name;
    private String email;
    private Role role;
    private ActiveStatus activeStatus;

    public static MemberResponse from(Member m) {
        return MemberResponse.builder()
                .id(m.getId())
                .username(m.getUsername())
                .name(m.getName())
                .email(m.getEmail())
                .role(m.getRole())
                .activeStatus(m.getActiveStatus())
                .build();
    }
}