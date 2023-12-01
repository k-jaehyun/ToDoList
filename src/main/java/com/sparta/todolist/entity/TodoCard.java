package com.sparta.todolist.entity;

import com.sparta.todolist.dto.TodoCardRequestDto;
import com.sparta.todolist.dto.TodoCardResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "todoCard")
@NoArgsConstructor
public class TodoCard extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="content",length = 5000)
    private String content;

    @Column(name="isdone")
    private Boolean isdone;

    @OneToMany(mappedBy = "todoCard", fetch = FetchType.EAGER)
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public TodoCard(TodoCardRequestDto requestDto, User user) {
        this.title= requestDto.getTitle();
        this.content= requestDto.getContent();
        this.user=user;
        this.isdone=false;
    }


    public void update(TodoCardRequestDto requestDto) {
        this.title= requestDto.getTitle();
        this.content= requestDto.getContent();
    }

    public void update(TodoCardResponseDto responseDto) {
        this.isdone = responseDto.getIsdone();
    }
}
