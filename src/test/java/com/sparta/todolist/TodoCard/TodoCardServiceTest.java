package com.sparta.todolist.TodoCard;

import com.sparta.todolist.Comment.Comment;
import com.sparta.todolist.Comment.CommentRepository;
import com.sparta.todolist.TodoCard.dto.TodoCardRequestDto;
import com.sparta.todolist.TodoCard.dto.TodoCardResponseDto;
import com.sparta.todolist.TodoCard.dto.TodoCardWithCommentsResponseDto;
import com.sparta.todolist.User.User;
import com.sparta.todolist.User.UserRepository;
import com.sparta.todolist.jwt.JwtUtil;
import com.sparta.todolist.security.UserDetailsImpl;
import jakarta.annotation.security.RunAs;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        // given
        TodoCardService todoCardService = new TodoCardService(todoCardRepository,jwtUtil,userRepository,commentRepository);

        String token = jwtUtil.createToken(user.getUsername());  // 왜 애로 하면 토큰 인증이 안될까요 -> 그래서 userDetails에서 받아오긴 했습니다.

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

//        //왜 null이 뜨는지...
//        TodoCard todoCard = new TodoCard(1L, todoCardRequestDto.getTitle(), todoCardRequestDto.getContent(), false,null,user);
//        given(todoCardRepository.save(new TodoCard(todoCardRequestDto,user))).willReturn(todoCard);
//        // 생성된 TodoCard 객체를 스텁으로 설정할 때와 테스트에서 저장된 TodoCard 객체를 생성할 때 서로 다른 객체가 사용됨.
//        // 이것이 Mockito의 PotentialStubbingProblem 예외를 발생시키는 이유

        // 위에 대신 이렇게 설정하면 돌아가긴 하지만...
        given(todoCardRepository.save(ArgumentMatchers.any(TodoCard.class))).willReturn(new TodoCard(todoCardRequestDto,user));

        // when
        TodoCardResponseDto result = todoCardService.createTodoCard(todoCardRequestDto,token,userDetails);

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