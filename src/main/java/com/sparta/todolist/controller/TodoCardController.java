package com.sparta.todolist.controller;

import com.sparta.todolist.dto.TodoCardRequestDto;
import com.sparta.todolist.dto.TodoCardResponseDto;
import com.sparta.todolist.service.TodoCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoCardController {

    private final TodoCardService todoCardService;

    @PostMapping("/auth/post")
    public TodoCardResponseDto createTodoCard(@RequestBody TodoCardRequestDto requestDto) {
        return todoCardService.createTodoCard(requestDto);
    }

}
