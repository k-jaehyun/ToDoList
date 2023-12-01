package com.sparta.todolist.TodoCard.dto;

import com.sparta.todolist.Comment.dto.CommentResponseDto;
import com.sparta.todolist.TodoCard.TodoCard;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TodoCardWithCommentsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiredAt;
    private Boolean isdone;

    private List<CommentResponseDto> commentList;


    public TodoCardWithCommentsResponseDto(TodoCard todoCard, List<CommentResponseDto> commentResponseDtoList) {
        this.id = todoCard.getId();
        this.title=todoCard.getTitle();
        this.content=todoCard.getContent();
        this.username=todoCard.getUser().getUsername();
        this.createdAt=todoCard.getCreatedAt();
        this.modifiredAt=todoCard.getModifiedAt();
        this.isdone=todoCard.getIsdone();
        this.commentList=commentResponseDtoList;
    }
}
