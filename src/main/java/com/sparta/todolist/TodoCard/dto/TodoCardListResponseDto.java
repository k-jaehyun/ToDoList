package com.sparta.todolist.TodoCard.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TodoCardListResponseDto {

    private String username;
    private List<TodoCardResponseDto> eachUsersCardList;


    public TodoCardListResponseDto(String username, List<TodoCardResponseDto> value) {
        this.username=username;
        this.eachUsersCardList=value;
    }
}
