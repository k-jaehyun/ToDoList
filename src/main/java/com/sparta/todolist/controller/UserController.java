package com.sparta.todolist.controller;

import com.sparta.todolist.dto.SignupRequestDto;
import com.sparta.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public String signup(@RequestBody SignupRequestDto requestDto) {

        userService.signup(requestDto);


        return "성공?";
    }

    @GetMapping("/user/login")
    public String loginPage() {
        return "로그인페이지 반환";
    }




}
