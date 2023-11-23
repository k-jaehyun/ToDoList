package com.sparta.todolist.service;

import com.sparta.todolist.dto.CommentRequestDto;
import com.sparta.todolist.dto.CommentResponsDto;
import com.sparta.todolist.entity.Comment;
import com.sparta.todolist.entity.TodoCard;
import com.sparta.todolist.entity.TodoCardComment;
import com.sparta.todolist.entity.User;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.repository.CommentRepository;
import com.sparta.todolist.repository.TodoCardCommentRepository;
import com.sparta.todolist.repository.TodoCardRepository;
import com.sparta.todolist.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TodoCardRepository todoCardRepository;
    private final TodoCardCommentRepository todoCardCommentRepository;
    public CommentResponsDto createComment(Long cardId, CommentRequestDto requestDto, String tokenValue) {
        String token = jwtUtil.substringToken(tokenValue);
        Claims info = jwtUtil.getUserInfoFromToken(token);
        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                new NullPointerException("Not Found User"));

        TodoCard todoCard = validateCardId(cardId);

        Comment comment = commentRepository.save(new Comment(requestDto,user,todoCard));
        todoCardCommentRepository.save(new TodoCardComment(todoCard,comment));

        return new CommentResponsDto(comment);
    }

    public List<CommentResponsDto> getCommentList(Long cardId) {
        validateCardId(cardId);
        return commentRepository.findByTodoCardCommentList_TodoCardId(cardId).stream().map(CommentResponsDto::new).toList();
    }

    private TodoCard validateCardId(Long cardId) {
        return todoCardRepository.findById(cardId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 cardId입니다."));
    }
}
