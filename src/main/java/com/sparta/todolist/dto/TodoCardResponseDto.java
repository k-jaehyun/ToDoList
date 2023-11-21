package com.sparta.todolist.dto;

import com.sparta.todolist.entity.TodoCard;
import com.sparta.todolist.entity.TodoCardComment;
import com.sparta.todolist.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TodoCardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiredAt;
    private Boolean isdone;

    private List<TodoCardComment> todoCardCommentList = new ArrayList<>();


    public TodoCardResponseDto(TodoCard todoCard, String username) {
        this.id= todoCard.getId();
        this.title=todoCard.getTitle();
        this.content=todoCard.getContent();
        this.createdAt=todoCard.getCreatedAt();
        this.username=username;
    }

    public TodoCardResponseDto(TodoCard todoCard) {
        this.id = todoCard.getId();
        this.title=todoCard.getTitle();
        this.content=todoCard.getContent();
        this.username=todoCard.getUser().getUsername();
        this.createdAt=todoCard.getCreatedAt();
        this.modifiredAt=todoCard.getModifiedAt();
    }

    public TodoCardResponseDto(Boolean isdone) {
        this.isdone=isdone;
    }


}
