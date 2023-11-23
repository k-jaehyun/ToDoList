package com.sparta.todolist.controller;

import com.sparta.todolist.dto.CommentRequestDto;
import com.sparta.todolist.dto.CommentResponsDto;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{cardId}")
    public CommentResponsDto createComment(@PathVariable Long cardId, @RequestBody CommentRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return commentService.createComment(cardId,requestDto,tokenValue);
    }

    @GetMapping("/auth/comments/{cardId}")
    public List<CommentResponsDto> getCommentList(@PathVariable Long cardId) {
        return commentService.getCommentList(cardId);
    }
}
