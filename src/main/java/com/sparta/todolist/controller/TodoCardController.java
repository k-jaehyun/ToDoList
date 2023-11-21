package com.sparta.todolist.controller;

import com.sparta.todolist.dto.TodoCardRequestDto;
import com.sparta.todolist.dto.TodoCardResponseDto;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.service.TodoCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoCardController {

    private final TodoCardService todoCardService;

    @PostMapping("/cards/post")
    public TodoCardResponseDto createTodoCard(@RequestBody TodoCardRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String cookeValue) {
        return todoCardService.createTodoCard(requestDto,cookeValue);
    }

    @GetMapping("/auth/get-card/{todoID}")
    public TodoCardResponseDto getTodoCard(@PathVariable Long todoID) {
        return todoCardService.getTodoCard(todoID);
    }

}
