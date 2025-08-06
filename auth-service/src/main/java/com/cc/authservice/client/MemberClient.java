package com.cc.authservice.client;


import com.cc.authservice.dto.response.MemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberClient {

    @GetMapping("/members/{id}")
    MemberResponse getById(@PathVariable("id") Long id);

    @GetMapping("/members/email/{email}")
    MemberResponse getByEmail(@PathVariable("email") String email);
}
