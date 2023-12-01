package com.sparta.todolist.service;

import com.sparta.todolist.dto.CommentRequestDto;
import com.sparta.todolist.dto.CommentResponseDto;
import com.sparta.todolist.dto.TodoCardWithCommentsResponseDto;
import com.sparta.todolist.entity.Comment;
import com.sparta.todolist.entity.TodoCard;
import com.sparta.todolist.entity.User;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.repository.CommentRepository;
import com.sparta.todolist.repository.TodoCardRepository;
import com.sparta.todolist.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TodoCardRepository todoCardRepository;

    public TodoCardWithCommentsResponseDto createComment(Long cardId, CommentRequestDto requestDto, String tokenValue) {
        User user = validateToken(tokenValue);

        TodoCard todoCard = validateCardId(cardId);

        commentRepository.save(new Comment(requestDto,user,todoCard));

        return new TodoCardWithCommentsResponseDto(todoCard, findCommentResponseDtoByCardId(cardId));
    }

    public List<CommentResponseDto> getCommentList(Long cardId) {
        validateCardId(cardId);

        return findCommentResponseDtoByCardId(cardId);
    }

    @Transactional
    public List<CommentResponseDto> updateComment(Long cardId, Long commentId, String tokenValue, CommentRequestDto requestDto) {
        User user = validateToken(tokenValue);
        TodoCard todoCard = validateCardId(cardId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if (!user.getUsername().equals(comment.getUser().getUsername())) {
            throw new IllegalArgumentException("해당 유저가 아닙니다.");
        }

        comment.update(requestDto);

        return findCommentResponseDtoByCardId(cardId);
    }


    public TodoCardWithCommentsResponseDto deleteComment(Long cardId, Long commentId, String tokenValue) {
        User user = validateToken(tokenValue);
        TodoCard todoCard = validateCardId(cardId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("commentId를 찾을 수 없습니다."));
        if(!user.getUsername().equals(todoCard.getUser().getUsername())) {
            throw new IllegalArgumentException("해당 유저가 아닙니다.");
        }

        commentRepository.delete(comment);

        return new TodoCardWithCommentsResponseDto(todoCard, findCommentResponseDtoByCardId(cardId));
    }


    private TodoCard validateCardId(Long cardId) {
        return todoCardRepository.findById(cardId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 cardId입니다."));
    }

    private User validateToken(String tokenValue) {
        String token = jwtUtil.substringToken(tokenValue);
        Claims info = jwtUtil.getUserInfoFromToken(token);
        return userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                new NullPointerException("Not Found User"));
    }

    private List<CommentResponseDto> findCommentResponseDtoByCardId(Long cardId) {
        return commentRepository.findAllByTodoCardId(cardId).stream().map(CommentResponseDto::new).toList();
    }
}
