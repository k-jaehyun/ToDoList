package com.sparta.todolist.dto;

import com.sparta.todolist.entity.Comment;
import com.sparta.todolist.entity.TodoCard;
import lombok.Getter;

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

//    private List<Comment> commentList = new ArrayList<>();

    public TodoCardResponseDto(TodoCard todoCard) {
        this.id = todoCard.getId();
        this.title=todoCard.getTitle();
        this.content=todoCard.getContent();
        this.username=todoCard.getUser().getUsername();
        this.createdAt=todoCard.getCreatedAt();
        this.modifiredAt=todoCard.getModifiedAt();
        this.isdone=todoCard.getIsdone();
//        this.commentList=todoCard.getCommentList();
    }

    public TodoCardResponseDto(Boolean isdone) {
        this.isdone=isdone;
    }


}
