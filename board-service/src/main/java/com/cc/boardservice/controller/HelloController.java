package com.cc.boardservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Board Service!";
    }
}
