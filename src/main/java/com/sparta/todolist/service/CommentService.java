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
import org.springframework.transaction.annotation.Transactional;

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
        User user = validateToken(tokenValue);

        TodoCard todoCard = validateCardId(cardId);

        Comment comment = commentRepository.save(new Comment(requestDto,user,todoCard));
        todoCardCommentRepository.save(new TodoCardComment(todoCard,comment));

        return new CommentResponsDto(comment);
    }

    public List<CommentResponsDto> getCommentList(Long cardId) {
        validateCardId(cardId);
        return commentRepository.findByTodoCardCommentList_TodoCardId(cardId).stream().map(CommentResponsDto::new).toList();
    }

    @Transactional
    public CommentResponsDto updateComment(Long cardId, Long commentId, String tokenValue, CommentRequestDto requestDto) {
        User user = validateToken(tokenValue);
        TodoCard todoCard = validateCardId(cardId);
        if(!user.getUsername().equals(todoCard.getUser().getUsername())) {
            throw new IllegalArgumentException("해당 유저가 아닙니다.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        TodoCardComment todoCardComment = todoCardCommentRepository.findByTodoCardIdAndCommentId(todoCard.getId(),comment.getId());

        comment.update(requestDto);
        todoCardComment.update(comment);

        return new CommentResponsDto(comment);
    }


    public void deleteComment(Long cardId, Long commentId, String tokenValue) {
        User user = validateToken(tokenValue);
        TodoCard todoCard = validateCardId(cardId);
        if(!user.getUsername().equals(todoCard.getUser().getUsername())) {
            throw new IllegalArgumentException("해당 유저가 아닙니다.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("commentId를 찾을 수 없습니다."));
        TodoCardComment todoCardComment = todoCardCommentRepository.findByTodoCardIdAndCommentId(todoCard.getId(),comment.getId());

        todoCardCommentRepository.delete(todoCardComment);
        commentRepository.delete(comment);
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
}
