package com.sparta.todolist.service;

import com.sparta.todolist.dto.TodoCardRequestDto;
import com.sparta.todolist.dto.TodoCardResponseDto;
import com.sparta.todolist.entity.TodoCard;
import com.sparta.todolist.entity.User;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.repository.TodoCardRepository;
import com.sparta.todolist.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoCardService {

    private final TodoCardRepository todoCardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    public TodoCardResponseDto createTodoCard(TodoCardRequestDto requestDto, String tokenValue) {

        String token = jwtUtil.substringToken(tokenValue);
        Claims info = jwtUtil.getUserInfoFromToken(token);
        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                new NullPointerException("Not Found User"));
        TodoCard todoCard = todoCardRepository.save((new TodoCard(requestDto,user)));

        return new TodoCardResponseDto(todoCard, user.getUsername());
    }
}
