package com.sparta.todolist.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todolist.dto.LoginRequestDto;
import com.sparta.todolist.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {  //이 필터를 상속받아서 구체적으로 구현해서 사용
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");   // UsernamePasswordAuthenticationFilter를 호출하면 이 메서드를 쓸 수 있음. -> 우리가 지정한 Post 방식의 로그인 url임
    }

    @Override   //로그인 시도하는 메서드 오버라이드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            // ObjectMapper: Json형태의 데이터를 Object혈태로 바꿈
            // request.getInputStream() 가 Json 형식의 데이터임
            // LoginRequestDto.class : 변환할 오브젝트의 타입

            return getAuthenticationManager().authenticate(  //AuthenticationManager의 authenticate메서드: 검증 수행 -> 그 안에 토큰 넣어준거임
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    //성공했을 때
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        String token = jwtUtil.createToken(username);
        jwtUtil.addJwtToCookie(token, response);
    }

    //실패했을 때
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);  //401: unautherized
    }
}
