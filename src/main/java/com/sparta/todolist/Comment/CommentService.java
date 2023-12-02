package com.sparta.todolist.Comment;

import com.sparta.todolist.Comment.dto.CommentRequestDto;
import com.sparta.todolist.Comment.dto.CommentResponseDto;
import com.sparta.todolist.TodoCard.dto.TodoCardWithCommentsResponseDto;
import com.sparta.todolist.TodoCard.TodoCard;
import com.sparta.todolist.User.User;
import com.sparta.todolist.exception.UnAuthorizedException;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.TodoCard.TodoCardRepository;
import com.sparta.todolist.User.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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
        Comment comment = validateCommentId(commentId);
        validateCommentUser(comment,user);

        comment.update(requestDto);

        return findCommentResponseDtoByCardId(cardId);
    }


    public TodoCardWithCommentsResponseDto deleteComment(Long cardId, Long commentId, String tokenValue) {
        User user = validateToken(tokenValue);
        TodoCard todoCard = validateCardId(cardId);
        Comment comment = validateCommentId(commentId);
        validateCommentUser(comment,user);

        commentRepository.delete(comment);

        return new TodoCardWithCommentsResponseDto(todoCard, findCommentResponseDtoByCardId(cardId));
    }


    private TodoCard validateCardId(Long cardId) {
        return todoCardRepository.findById(cardId).orElseThrow(()-> new NoSuchElementException("선택한 cardId: ("+cardId+")가 존재하지 않습니다."));
    }

    private User validateToken(String tokenValue) {
        String token = jwtUtil.substringToken(tokenValue);
        Claims info = jwtUtil.getUserInfoFromToken(token);
        return userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                new UnAuthorizedException("Not Found User From DB By Token"));
    }

    private List<CommentResponseDto> findCommentResponseDtoByCardId(Long cardId) {
        return commentRepository.findAllByTodoCardId(cardId).stream().map(CommentResponseDto::new).toList();
    }

    private Comment validateCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("선택한 commentId: ("+commentId+")가 존재하지 않습니다."));
    }

    private void validateCommentUser(Comment comment, User user) {
        if (!user.getUsername().equals(comment.getUser().getUsername())) {
            throw new UnAuthorizedException("해당 유저가 아닙니다.");
        }
    }
}
