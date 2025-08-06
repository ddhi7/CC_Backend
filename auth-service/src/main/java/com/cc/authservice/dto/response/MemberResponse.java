package com.cc.authservice.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String role;
    private String activeStatus;
}
