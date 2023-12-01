package com.sparta.todolist.TodoCard.dto;

import com.sparta.todolist.TodoCard.TodoCard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoCardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiredAt;
    private Boolean isdone;

    public TodoCardResponseDto(TodoCard todoCard) {
        this.id = todoCard.getId();
        this.title=todoCard.getTitle();
        this.content=todoCard.getContent();
        this.username=todoCard.getUser().getUsername();
        this.createdAt=todoCard.getCreatedAt();
        this.modifiredAt=todoCard.getModifiedAt();
        this.isdone=todoCard.getIsdone();
    }

    public TodoCardResponseDto(Boolean isdone) {
        this.isdone=isdone;
    }


}
