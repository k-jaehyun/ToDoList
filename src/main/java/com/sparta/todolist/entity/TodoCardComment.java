//package com.sparta.todolist.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Table(name = "todoCard_commnet")
//@NoArgsConstructor
//public class TodoCardComment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "todoCard_id", nullable = false)
//    private TodoCard todoCard;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "comment_id", nullable = false)
//    private Comment comment;
//
//    public TodoCardComment(TodoCard todoCard, Comment comment) {
//        this.todoCard = todoCard;
//        this.comment = comment;
//    }
//
//    public void update(Comment comment) {
//        this.comment=comment;
//    }
//}
