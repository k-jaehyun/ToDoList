package com.sparta.todolist.TodoCard;

import com.sparta.todolist.Comment.Comment;
import com.sparta.todolist.Comment.CommentRepository;
import com.sparta.todolist.TodoCard.dto.TodoCardListResponseDto;
import com.sparta.todolist.TodoCard.dto.TodoCardRequestDto;
import com.sparta.todolist.TodoCard.dto.TodoCardResponseDto;
import com.sparta.todolist.TodoCard.dto.TodoCardWithCommentsResponseDto;
import com.sparta.todolist.User.User;
import com.sparta.todolist.User.UserRepository;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.security.UserDetailsImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
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
    private TodoCardRequestDto todoCardRequestDto2;
    private User user;
    private User user2;
    private UserDetailsImpl userDetails;
    private UserDetailsImpl userDetails2;

    @BeforeEach
    void setUp() {
        todoCardRequestDto = new TodoCardRequestDto("제목", "내용");
        todoCardRequestDto2 = new TodoCardRequestDto("제목2", "내용2");
        user = new User("유저이름", "비밀번호");
        user2 = new User("유저이름2", "비밀번호2");
        userDetails = new UserDetailsImpl(user);
        userDetails2 = new UserDetailsImpl(user2);
    }

    @Order(1)
    @Test
    @DisplayName("createTodoCard")
    void createTodoCard() {
        // given
        TodoCardService todoCardService = new TodoCardService(todoCardRepository,jwtUtil,userRepository,commentRepository);

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        // save를 어떻게 검증하는지 모르겠습니다.
        given(todoCardRepository.save(ArgumentMatchers.any(TodoCard.class))).willReturn(new TodoCard(todoCardRequestDto,user));

        // when
        TodoCardResponseDto result = todoCardService.createTodoCard(todoCardRequestDto,userDetails);

        // then
        assertEquals(result.getUsername(),user.getUsername());
        assertEquals(result.getTitle(),todoCardRequestDto.getTitle());
        assertEquals(result.getContent(),todoCardRequestDto.getContent());
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
        // given
        TodoCard todoCard1 = new TodoCard(todoCardRequestDto, user);
        TodoCard todoCard2 = new TodoCard(todoCardRequestDto, user2);
        List<TodoCard> todoCardList = Arrays.asList(todoCard1,todoCard2);
        given(todoCardRepository.findAllByOrderByCreatedAtDesc()).willReturn(todoCardList);

        TodoCardService todoCardService = new TodoCardService(todoCardRepository,jwtUtil,userRepository,commentRepository);

        // when
        List<TodoCardListResponseDto> result = todoCardService.getTodoCardList();

        // then
        assertEquals(2,result.size());
        assertEquals(result.get(0).getUsername(),user.getUsername());
        assertEquals(result.get(1).getUsername(),user2.getUsername());
        assertEquals(1,result.get(0).getEachUsersCardList().size());
        assertEquals(1,result.get(1).getEachUsersCardList().size());
        assertEquals(todoCard1.getId(),result.get(0).getEachUsersCardList().get(0).getId());
        assertEquals(todoCard2.getId(),result.get(1).getEachUsersCardList().get(0).getId());
        assertEquals(todoCard1.getContent(),result.get(0).getEachUsersCardList().get(0).getContent());
        assertEquals(todoCard2.getContent(),result.get(1).getEachUsersCardList().get(0).getContent());
    }

    @Order(4)
    @Test // 왜 실행이 안되는지 모르겠습니다.
    void updateTodoCard() {
//        // given
//        Long cardId = 1L;
//        TodoCardService todoCardService = new TodoCardService(todoCardRepository,jwtUtil,userRepository,commentRepository);
//
//        TodoCard todoCard = new TodoCard(todoCardRequestDto,user);
//
//        given(todoCardRepository.findById(cardId)).willReturn(Optional.of(todoCard));
//        given(todoCardService.verifyUserAndGetTodoCard(user,cardId)).willReturn(todoCard);
//        // when
//        TodoCardResponseDto result = todoCardService.updateTodoCard(cardId,todoCardRequestDto,userDetails);
//        // then
//        assertEquals(result.getUsername(),user.getUsername());
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
        @Test // 왜 실행이 안되는지 모르겠습니다.
        void findUserByToken() throws UnsupportedEncodingException {
//            // given
//            String token = jwtUtil.createToken(user.getUsername());
//            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
//            TodoCardService todoCardService = new TodoCardService(todoCardRepository,jwtUtil,userRepository,commentRepository);
//
//            // when
//            User result = todoCardService.findUserByToken(token);
//
//            // then
//            assertEquals(result.getUsername(),user.getUsername());
        }

        @Order(2)
        @Test
        void verifyUserAndGetTodoCard() {
            // given
            Long cardId = 1L;
            TodoCard todoCard = new TodoCard(todoCardRequestDto,user);
            given(todoCardRepository.findById(cardId)).willReturn(Optional.of(todoCard));

            TodoCardService todoCardService = new TodoCardService(todoCardRepository,jwtUtil,userRepository,commentRepository);

            // when
            TodoCard result = todoCardService.verifyUserAndGetTodoCard(user,cardId);

            // then
            assertEquals(result.getUser().getUsername(),user.getUsername());
            assertNotEquals(result.getUser().getUsername(),user2.getUsername());
        }
    }
}