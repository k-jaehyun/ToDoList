package com.sparta.todolist.TodoCard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoCardRepository extends JpaRepository<TodoCard, Long> {
    List<TodoCard> findAllByOrderByCreatedAtDesc();
}
