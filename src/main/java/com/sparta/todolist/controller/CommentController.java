package com.sparta.todolist.controller;

import com.sparta.todolist.dto.CommentRequestDto;
import com.sparta.todolist.dto.CommentResponseDto;
import com.sparta.todolist.dto.TodoCardResponseDto;
import com.sparta.todolist.dto.TodoCardWithCommentsResponseDto;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards/{cardId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public TodoCardWithCommentsResponseDto createComment(@PathVariable Long cardId, @RequestBody CommentRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return commentService.createComment(cardId,requestDto,tokenValue);
    }

    @GetMapping("")
    public List<CommentResponseDto> getCommentList(@PathVariable Long cardId) {
        return commentService.getCommentList(cardId);
    }

    @PatchMapping("/{commentId}")
    public List<CommentResponseDto> updateComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long cardId, @PathVariable Long commentId, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return commentService.updateComment(cardId,commentId,tokenValue,requestDto);
    }

    @DeleteMapping("/{commentId}")
    public TodoCardWithCommentsResponseDto deleteComment(@PathVariable Long cardId, @PathVariable Long commentId, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){
        return commentService.deleteComment(cardId,commentId,tokenValue);
    }
}
