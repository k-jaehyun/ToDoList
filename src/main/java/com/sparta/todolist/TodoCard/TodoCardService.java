package com.sparta.todolist.TodoCard;

import com.sparta.todolist.Comment.dto.CommentResponseDto;
import com.sparta.todolist.TodoCard.dto.TodoCardListResponseDto;
import com.sparta.todolist.TodoCard.dto.TodoCardRequestDto;
import com.sparta.todolist.TodoCard.dto.TodoCardResponseDto;
import com.sparta.todolist.TodoCard.dto.TodoCardWithCommentsResponseDto;
import com.sparta.todolist.User.User;
import com.sparta.todolist.exception.UnAuthorizedException;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.Comment.CommentRepository;
import com.sparta.todolist.User.UserRepository;
import com.sparta.todolist.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TodoCardService {

    private final TodoCardRepository todoCardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public TodoCardResponseDto createTodoCard(TodoCardRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        TodoCard todoCard = todoCardRepository.save((new TodoCard(requestDto,user)));

        return new TodoCardResponseDto(todoCard);
    }

    public TodoCardWithCommentsResponseDto getTodoCard(Long todoCardId) {
        List<CommentResponseDto> commentResponseDtoList
                =commentRepository.findAllByTodoCardId(todoCardId).stream().map(CommentResponseDto::new).toList();

        return new TodoCardWithCommentsResponseDto(findCardById(todoCardId), commentResponseDtoList);
    }



    public List<TodoCardListResponseDto> getTodoCardList() {
        Map<User,List<TodoCardResponseDto>> userTodoCardsMap = new HashMap<>();
        todoCardRepository.findAllByOrderByCreatedAtDesc().stream().forEach(
                a -> {
                    if (userTodoCardsMap.get(a.getUser()) == null) {
                        userTodoCardsMap.put(a.getUser(),new ArrayList<>(List.of(new TodoCardResponseDto(a))));
                    } else {
                        userTodoCardsMap.get(a.getUser()).add(new TodoCardResponseDto(a));
                    }
                }
        );

        List<TodoCardListResponseDto> response = new ArrayList<>();

        userTodoCardsMap.forEach( (key,value) -> response.add(new TodoCardListResponseDto(key.getUsername(), value)));

        return response;
    }

    @Transactional
    public TodoCardResponseDto updateTodoCard(Long cardid, TodoCardRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        TodoCard todoCard = verifyUserAndGetTodoCard(user,cardid);

        todoCard.update(requestDto);

        return new TodoCardResponseDto(todoCard);
    }

    @Transactional
    public TodoCardResponseDto updateIsDone(Long cardid,Boolean isdone , UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        TodoCard todoCard = verifyUserAndGetTodoCard(user,cardid);

        TodoCardResponseDto todoCardResponseDto = new TodoCardResponseDto(isdone);

        todoCard.update(todoCardResponseDto);

        return new TodoCardResponseDto(todoCard);
    }

    public User findUserByToken(String tokenValue) {
        try {
            String token = jwtUtil.substringToken(tokenValue);
            Claims info = jwtUtil.getUserInfoFromToken(token);
            return userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                    new UnAuthorizedException("Not Found User From DB By Token"));
        } catch (Exception e) {
            return null;
        }
    }

    public TodoCard verifyUserAndGetTodoCard(User user, Long cardid) {
        TodoCard todoCard = findCardById(cardid);
        if (!todoCard.getUser().getUsername().equals(user.getUsername())) {
            throw new UnAuthorizedException("해당 사용자가 아님.");
        }
        return todoCard;
    }

    private TodoCard findCardById(Long cardId) {
        return todoCardRepository.findById(cardId).orElseThrow(()->new NoSuchElementException("선택한 cardId: ("+cardId+")가 존재하지 않습니다."));
    }
}
