package com.sparta.todolist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="userName")
    private String userName;
    @Column(name="content",length = 5000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "todoCard_id", nullable = false)
    private TodoCard todoCard;

    @OneToMany(mappedBy = "comment")
    private List<TodoCardComment> todoCardCommentList = new ArrayList<>();


}
