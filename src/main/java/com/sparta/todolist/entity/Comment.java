package com.sparta.todolist.entity;

import com.sparta.todolist.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content",length = 5000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "todoCard_id", nullable = false)
    private TodoCard todoCard;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    public Comment(CommentRequestDto requestDto, User user, TodoCard todoCard) {
        this.user=user;
        this.content= requestDto.getContent();
        this.todoCard = todoCard;
    }

    public void update(CommentRequestDto requestDto) {
        this.content= requestDto.getContent();
    }
}
