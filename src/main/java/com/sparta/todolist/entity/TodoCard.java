package com.sparta.todolist.entity;

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
    @Column(name="userName")
    private String userName;
    @Column(name="content",length = 5000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Comment에 연결X

    @OneToMany(mappedBy = "todoCard")
    private List<TodoCardComment> todoCardCommentList = new ArrayList<>();
}
