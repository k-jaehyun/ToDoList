package com.sparta.todolist.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "LoggingFilter")   // 로깅 찍힐 때 topic으로 이름을 넣어준 것.
@Component
@Order(1)  // 필터의 순서를 지정해주는 것. (인증 및 인가보다 먼저 시키고 싶어서 1로 줌.
public class LoggingFilter implements Filter {   //필터 역할을 수행하기 위해 필터 인터페이스를 implements 받음.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {   //FilterChain: 필터를 이동 할 때 사용
        // 전처리
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;   //HttpServletRequest로 바꿔서 반환
        String url = httpServletRequest.getRequestURI();
        log.info(url);  //어떤 요청인지 로그를 찍는 것임.  // @Slf4j 달고, 이걸 써준다음 url을 넣으면 로그를 찍을 수 있음  --> log.error 로 에러를 찍을 수도 있음.

        chain.doFilter(request, response); // 다음 Filter 로 이동

        // 후처리 (필터를 다 거치고 Dispatchservlet도 지나고 컨트롤러를 통해 수행된 다음 다시 응답이 반환되어 필터를 거치게 되는데, 필터거치고 컨트롤러 지나고 다시 돌아와서 여기로 옴 --> 실행시키고 로그인페이지 열면 로그 찍히는걸 볼 수 있음. -> 보면 이해 됨.)
        log.info("비즈니스 로직 완료");
    }
}
