package com.sparta.todolist.service;

import com.sparta.todolist.dto.TodoCardRequestDto;
import com.sparta.todolist.dto.TodoCardResponseDto;
import com.sparta.todolist.entity.TodoCard;
import com.sparta.todolist.entity.User;
import com.sparta.todolist.repository.TodoCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoCardService {

    private final TodoCardRepository todoCardRepository;


    public TodoCardResponseDto createTodoCard(TodoCardRequestDto requestDto) {
        TodoCard todoCard = todoCardRepository.save((new TodoCard(requestDto)));
        return new TodoCardResponseDto(todoCard);
    }
}
