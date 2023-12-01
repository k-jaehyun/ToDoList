package com.sparta.todolist.dto;

import com.sparta.todolist.entity.Comment;
import com.sparta.todolist.entity.TodoCard;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
