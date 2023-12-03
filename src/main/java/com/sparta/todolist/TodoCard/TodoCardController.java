package com.sparta.todolist.TodoCard;

import com.sparta.todolist.TodoCard.dto.TodoCardListResponseDto;
import com.sparta.todolist.TodoCard.dto.TodoCardRequestDto;
import com.sparta.todolist.TodoCard.dto.TodoCardResponseDto;
import com.sparta.todolist.TodoCard.dto.TodoCardWithCommentsResponseDto;
import com.sparta.todolist.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class TodoCardController {

    private final TodoCardService todoCardService;

    @PostMapping("/post")
    public TodoCardResponseDto createTodoCard(@RequestBody TodoCardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoCardService.createTodoCard(requestDto, userDetails);
    }

    @GetMapping("/{cardId}")
    public TodoCardWithCommentsResponseDto getTodoCard(@PathVariable Long cardId) {
        return todoCardService.getTodoCard(cardId);
    }

    @GetMapping("/list")
    public List<TodoCardListResponseDto> getTodoCardList() {
        return todoCardService.getTodoCardList();
    }

    @PatchMapping("/{cardid}")
    public TodoCardResponseDto updateTodoCard(@PathVariable Long cardid, @RequestBody TodoCardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoCardService.updateTodoCard(cardid, requestDto, userDetails);
    }

    @PatchMapping("/{cardid}/{isdone}")
    public TodoCardResponseDto completeTodoCard(@PathVariable Long cardid, @PathVariable Boolean isdone, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoCardService.updateIsDone(cardid, isdone, userDetails);
    }

}
