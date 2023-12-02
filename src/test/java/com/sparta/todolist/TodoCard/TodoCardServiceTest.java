package com.sparta.todolist.TodoCard;

import com.sparta.todolist.Comment.Comment;
import com.sparta.todolist.Comment.CommentRepository;
import com.sparta.todolist.TodoCard.dto.TodoCardRequestDto;
import com.sparta.todolist.TodoCard.dto.TodoCardWithCommentsResponseDto;
import com.sparta.todolist.User.User;
import com.sparta.todolist.User.UserRepository;
import com.sparta.todolist.jwt.JwtUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("TodoCardServiceTest")
class TodoCardServiceTest {

    @Mock
    TodoCardRepository todoCardRepository;
    @Mock
    JwtUtil jwtUtil;
    @Mock
    UserRepository userRepository;
    @Mock
    CommentRepository commentRepository;

    private TodoCardRequestDto todoCardRequestDto;
    private User user;

    @BeforeEach
    void setUp() {
        todoCardRequestDto = new TodoCardRequestDto("제목", "내용");
        user = new User("유저이름", "비밀번호");
    }

    @Order(1)
    @Test
    @DisplayName("createTodoCard") //H2 연결하고싶다
    void createTodoCard() {
//        // given
//        todoCardRepository.save(new TodoCard(1L,todoCardRequestDto.getTitle(),todoCardRequestDto.getContent(),false,null,user));
//
//        TodoCardService todoCardService = new TodoCardService(todoCardRepository,jwtUtil,userRepository,commentRepository);
//
//        given(todoCardService.findUserByToken("TestToken")).willReturn(user);
//        // when
//        TodoCardResponseDto result = todoCardService.createTodoCard(todoCardRequestDto,"TestToken");
//
//        // then
//        assertEquals(result.getUsername(),user.getUsername());
//        assertEquals(result.getTitle(),todoCardRequestDto.getTitle());
//        assertEquals(result.getContent(),todoCardRequestDto.getContent());
    }

    @Order(2)
    @Test
    void getTodoCard() {
        //given
        Long todoCardId = 1L;
        TodoCard todoCard = new TodoCard(todoCardRequestDto, user);
        todoCardRepository.save(todoCard);

        Comment comment1 = new Comment(1L,"내용1",todoCard,user);
        Comment comment2 = new Comment(2L,"내용2",todoCard,user);
        List<Comment> commentList = Arrays.asList(comment1,comment2);
        given(commentRepository.findAllByTodoCardId(todoCardId)).willReturn(commentList);

        given(todoCardRepository.findById(todoCardId)).willReturn(Optional.of(todoCard));

        TodoCardService todoCardService = new TodoCardService(todoCardRepository,jwtUtil,userRepository,commentRepository);

        // when
        TodoCardWithCommentsResponseDto result = todoCardService.getTodoCard(todoCardId);

        // then
        for (int i=0;i<commentList.size();i++) {
            assertEquals(result.getCommentList().get(i).getContent(), commentList.get(i).getContent());
        }
    }


    @Order(3)
    @Test
    void getTodoCardList() {
    }

    @Order(4)
    @Test
    void updateTodoCard() {
    }

    @Order(5)
    @Test
    void updateIsDone() {
    }

    @Order(6)
    @Nested
    @DisplayName("methods test")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class Methods {
        @Order(1)
        @Test
        void findUserByToken() {
        }

        @Order(2)
        @Test
        void verifyUserAndGetTodoCard() {
        }
    }
}