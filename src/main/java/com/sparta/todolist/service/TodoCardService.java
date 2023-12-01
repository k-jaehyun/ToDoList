package com.sparta.todolist.service;

import com.sparta.todolist.dto.TodoCardListResponseDto;
import com.sparta.todolist.dto.TodoCardRequestDto;
import com.sparta.todolist.dto.TodoCardResponseDto;
import com.sparta.todolist.entity.TodoCard;
import com.sparta.todolist.entity.User;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.repository.TodoCardRepository;
import com.sparta.todolist.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TodoCardService {

    private final TodoCardRepository todoCardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    public TodoCardResponseDto createTodoCard(TodoCardRequestDto requestDto, String tokenValue) {

        String token = jwtUtil.substringToken(tokenValue);
        Claims info = jwtUtil.getUserInfoFromToken(token);
        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                new NullPointerException("Not Found User"));
        TodoCard todoCard = todoCardRepository.save((new TodoCard(requestDto,user)));

        return new TodoCardResponseDto(todoCard, user.getUsername());
    }

    public TodoCardResponseDto getTodoCard(Long id) {
        return new TodoCardResponseDto(findCard(id));
    }

    private TodoCard findCard(Long id) {
        return todoCardRepository.findById(id).orElseThrow(()->new IllegalArgumentException("선택한 할일카드가 존재하지 않습니다."));
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
    public TodoCardResponseDto updateTodoCard(Long cardid, TodoCardRequestDto requestDto, String tokenValue) {
        User user = findUserByToken(tokenValue);

        TodoCard todoCard = verifyUser(user,cardid);

        todoCard.update(requestDto);

        return new TodoCardResponseDto(todoCard);
    }

    @Transactional
    public TodoCardResponseDto updateIsDone(Long cardid,Boolean isdone , String tokenValue) {
        User user = findUserByToken(tokenValue);

        TodoCard todoCard = verifyUser(user,cardid);

        TodoCardResponseDto todoCardResponseDto = new TodoCardResponseDto(isdone);

        todoCard.update(todoCardResponseDto);

        return new TodoCardResponseDto(todoCard);
    }

    public User findUserByToken(String tokenValue) {
        String token = jwtUtil.substringToken(tokenValue);
        Claims info = jwtUtil.getUserInfoFromToken(token);
        return userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                new NullPointerException("Not Found User"));
    }

    public TodoCard verifyUser(User user, Long cardid) {
        TodoCard todoCard = todoCardRepository.findById(cardid).orElseThrow(()->new IllegalArgumentException("존재하지 않는 cardid"));
        if (!todoCard.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 사용자가 아님.");
        }
        return todoCard;
    }
}
