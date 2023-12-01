package com.sparta.todolist.dto;

import com.sparta.todolist.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String username;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiredAt;

    public CommentResponseDto (Comment comment) {
        this.commentId=comment.getId();
        this.username=comment.getUser().getUsername();
        this.content=comment.getContent();
        this.createdAt=comment.getCreatedAt();
        this.modifiredAt=comment.getModifiedAt();
    }

}
