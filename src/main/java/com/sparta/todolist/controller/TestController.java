package com.sparta.todolist.controller;

import com.sparta.todolist.entity.User;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @GetMapping("/api/test")
    public String getProducts(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        System.out.println("TestController : 인증 완료");

        String token = jwtUtil.substringToken(tokenValue);
        Claims info = jwtUtil.getUserInfoFromToken(token);
        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                new NullPointerException("Not Found User"));


        return "유저 이름 : "+user.getUsername();
    }

    @GetMapping("/api/prod")
    public String getProd(){
        System.out.println("허허");
        return "하하";
    }
}
