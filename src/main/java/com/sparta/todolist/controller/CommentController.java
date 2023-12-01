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
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{cardId}")
    public TodoCardWithCommentsResponseDto createComment(@PathVariable Long cardId, @RequestBody CommentRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return commentService.createComment(cardId,requestDto,tokenValue);
    }

    @GetMapping("/{cardId}")
    public List<CommentResponseDto> getCommentList(@PathVariable Long cardId) {
        return commentService.getCommentList(cardId);
    }
//
//    @PatchMapping("/comments/{cardId}/{commentId}")
//    public CommentResponsDto updateComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long cardId, @PathVariable Long commentId, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
//        return commentService.updateComment(cardId,commentId,tokenValue,requestDto);
//    }
//
//    @DeleteMapping("comments/{cardId}/{commentId}")
//    public String deleteComment(@PathVariable Long cardId, @PathVariable Long commentId, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){
//        commentService.deleteComment(cardId,commentId,tokenValue);
//        return "삭제 성공";
//    }
}
