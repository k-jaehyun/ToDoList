package com.sparta.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todolist.Comment.CommentController;
import com.sparta.todolist.Comment.CommentService;
import com.sparta.todolist.TodoCard.TodoCardController;
import com.sparta.todolist.TodoCard.TodoCardService;
import com.sparta.todolist.User.User;
import com.sparta.todolist.User.UserController;
import com.sparta.todolist.User.UserService;
import com.sparta.todolist.User.dto.LoginRequestDto;
import com.sparta.todolist.config.WebSecurityConfig;
import com.sparta.todolist.security.UserDetailsImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {UserController.class, TodoCardController.class, CommentController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("ControllerTest")
class ControllerTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    TodoCardService todoCardService;

    @MockBean
    CommentService commentServicel;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetup() {
        String username = "user1";
        String password = "123546789";
        User testUser = new User(username,password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails,"",testUserDetails.getAuthorities());
    }

    @Order(1)
    @Nested
    @DisplayName("UserController test")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class UserController {
        @Order(1)
        @Test
        @DisplayName("login 테스트")
        void login() throws Exception {
            // given
            String username = "testUser";
            String password = "testPassword";
            LoginRequestDto loginRequestDto = new LoginRequestDto(username,password);

            // when - then
            mvc.perform(post("/api/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequestDto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.msg",is("로그인 성공")))
                    .andExpect(jsonPath("$.statusCode",is(HttpStatus.OK.value())))
                    .andDo(print());
        }

        @Order(2)
        @Test
        @DisplayName("signupTest")
        void signup() throws Exception {
//            MultiValueMap<String, String> signupRequestForm = new LinkedMultiValueMap<>();
//            signupRequestForm.add("username","user1");
//            signupRequestForm.add("password","123456789");
//
//            // when - then
//            mvc.perform(post("/api/users/signup")
//                            .params(signupRequestForm))
//                    .andExpect(status().isCreated())
//                    .andExpect(jsonPath("$.msg",is("회원가입 성공")))
//                    .andExpect(jsonPath("$.statusCode",is(HttpStatus.OK.value())))
//                    .andDo(print());
//
//        }
    }
//    @PostMapping("/signup")
//    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
//        try {
//            userService.signup(requestDto);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new CommonResponseDto("회원가입 성공",HttpStatus.OK.value()));
//    }


    @Test
    void createTodoCard() throws Exception {
        // when - then
    }


    @Test
    void getTodoCard() {
    }

    @Test
    void getTodoCardList() {
    }

    @Test
    void updateTodoCard() {
    }

    @Test
    void completeTodoCard() {
    }
}