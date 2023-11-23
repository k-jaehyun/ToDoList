package com.sparta.todolist.dto;

import com.sparta.todolist.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponsDto {
    private Long id;
    private String content;
    private String username;

    public CommentResponsDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUserName();
    }
}
