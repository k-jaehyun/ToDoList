package com.sparta.todolist.controller;

import com.sparta.todolist.dto.TodoCardListResponseDto;
import com.sparta.todolist.dto.TodoCardRequestDto;
import com.sparta.todolist.dto.TodoCardResponseDto;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.service.TodoCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class TodoCardController {

    private final TodoCardService todoCardService;

    @PostMapping("/post")
    public TodoCardResponseDto createTodoCard(@RequestBody TodoCardRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return todoCardService.createTodoCard(requestDto,tokenValue);
    }

    @GetMapping("/{todoID}")
    public TodoCardResponseDto getTodoCard(@PathVariable Long todoID) {
        return todoCardService.getTodoCard(todoID);
    }

    @GetMapping("/list")
    public List<TodoCardListResponseDto> getTodoCardList() {
        return todoCardService.getTodoCardList();
    }

    @PatchMapping("/{cardid}")
    public TodoCardResponseDto updateTodoCard(@PathVariable Long cardid, @RequestBody TodoCardRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return todoCardService.updateTodoCard(cardid, requestDto, tokenValue);
    }

    @PatchMapping("/{cardid}/{isdone}")
    public TodoCardResponseDto completeTodoCard(@PathVariable Long cardid, @PathVariable Boolean isdone, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return todoCardService.updateIsDone(cardid, isdone, tokenValue);
    }

}
