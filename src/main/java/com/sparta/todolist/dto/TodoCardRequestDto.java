package com.sparta.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoCardRequestDto {
    private String title;
    private String userName;
    private String content;
}
