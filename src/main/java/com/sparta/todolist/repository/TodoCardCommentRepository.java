package com.sparta.todolist.repository;

import com.sparta.todolist.entity.TodoCardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoCardCommentRepository extends JpaRepository<TodoCardComment, Long> {
}
