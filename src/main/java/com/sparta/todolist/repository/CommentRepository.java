package com.sparta.todolist.repository;

import com.sparta.todolist.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTodoCardId(Long todoCardId);
}
