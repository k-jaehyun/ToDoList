package com.sparta.todolist.TodoCard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoCardRequestDto {
    private String title;
    private String content;
}
