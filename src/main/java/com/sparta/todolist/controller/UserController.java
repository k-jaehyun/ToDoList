package com.sparta.todolist.controller;

import com.sparta.todolist.dto.LoginRequestDto;
import com.sparta.todolist.dto.SignupRequestDto;
import com.sparta.todolist.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public String signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "허용된 username 또는 password 값이 아닙니다.";
        }

        userService.signup(requestDto);

        return "회원가입 성공";
    }

//    @PostMapping("/user/login")
//    public String login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
//        try {
//            userService.login(requestDto, res);
//        } catch (Exception e) {
//            return "로그인실패";
//        }
//
//        return "로그인성공";
//    }




}
