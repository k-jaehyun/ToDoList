package com.sparta.todolist.repository;

import com.sparta.todolist.entity.TodoCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoCardRepository extends JpaRepository<TodoCard, Long> {
}
