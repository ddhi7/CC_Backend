package com.cc.memberservice.entity;

import com.cc.memberservice.entity.enums.ActiveStatus;
import com.cc.memberservice.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;

    @Builder
    public Member(String username, String name, Role role, String email, String password) {
        this.username = username;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.activeStatus = ActiveStatus.ACTIVE;
    }

    public void deactivate() {
        this.activeStatus = ActiveStatus.INACTIVE;
    }

    public void activate() {
        this.activeStatus = ActiveStatus.ACTIVE;
    }
}
