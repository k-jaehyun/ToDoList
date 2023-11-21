package com.sparta.todolist.dto;

import com.sparta.todolist.entity.TodoCard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoCardListResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    public TodoCardListResponseDto(TodoCard todoCard) {
        this.id= todoCard.getId();
        this.title=todoCard.getTitle();
        this.content=todoCard.getContent();
        this.username=todoCard.getUser().getUsername();
        this.createdAt=todoCard.getCreatedAt();
    }
}
