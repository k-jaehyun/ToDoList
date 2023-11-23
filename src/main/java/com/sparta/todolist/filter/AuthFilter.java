//package com.sparta.todolist.filter;
//
//import com.sparta.todolist.entity.User;
//import com.sparta.todolist.jwt.JwtUtil;
//import com.sparta.todolist.repository.UserRepository;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//
//@Slf4j(topic = "AuthFilter")
//@Component
//@RequiredArgsConstructor
//@Order(2)
//public class AuthFilter implements Filter {
//
//    // 아래 2개의 빈을 생성자로 주입받아와 사용
//    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String url = httpServletRequest.getRequestURI();
//
//        if (StringUtils.hasText(url) &&  //널, 공백인지 확인
//                (url.startsWith("/api/user") || url.startsWith("/api/auth") )
//        ) {
//            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행. css나 js도 막을 이유가 없음.
//            System.out.println("인증 처리를 하지 않는 url: "+url);
//            chain.doFilter(request, response); // 다음 Filter 로 이동
//        } else {   // 나머지 API 요청은 인증 처리 진행
//
//            // 토큰 확인
//            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);   //필터는 dispatcherServlet 보다 앞선 단계이기 때문에 @CookieValue 사용 못함. --> 직접 쿠키를 뽑아오는 메서드를 써줘야함.
//
//            if (StringUtils.hasText(tokenValue)) {   // 토큰이 존재하면 검증 시작
//                // JWT 토큰 substring
//                String token = jwtUtil.substringToken(tokenValue);
//
//                // 토큰 검증
//                if (!jwtUtil.validateToken(token)) {
//                    throw new IllegalArgumentException("Token Error");
//                }
//
//                // 토큰에서 사용자 정보 가져오기
//                Claims info = jwtUtil.getUserInfoFromToken(token);
//
//                User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
//                        new NullPointerException("Not Found User")
//                );
//
//                request.setAttribute("user", user);   //request가 controller에 넘어갈테니, 이 request에 "user"라는 이름으로 user 엔터티를 넘겨줌.
//                chain.doFilter(request, response); // 다음 Filter 로 이동
//            } else {
//                throw new IllegalArgumentException("Not Found Token");
//            }
//        }
//    }
//
//}
