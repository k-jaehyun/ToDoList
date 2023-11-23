package com.sparta.todolist.repository;

import com.sparta.todolist.dto.CommentRequestDto;
import com.sparta.todolist.dto.CommentResponsDto;
import com.sparta.todolist.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTodoCardCommentList_TodoCardId(Long cardId);
}
