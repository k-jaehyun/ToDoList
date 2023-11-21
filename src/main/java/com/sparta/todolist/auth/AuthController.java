package com.sparta.todolist.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class AuthController {

    public static final String AUTHORIZATION_HEADER = "Authorization";


    @GetMapping("/create-cookie")
    public String createCookie(HttpServletResponse res) {
        addCookie("쿠키 밸류",res);

        return "createCookie";
    }

    @GetMapping("/create-session")
    public String createSession(HttpServletRequest req) {
        HttpSession session = req.getSession(true);

        session.setAttribute(AUTHORIZATION_HEADER,"session value");

        return "createSession";
    }

    public static void addCookie(String cookieValue, HttpServletResponse res) {
        try {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
            //URLEncoder라는 클래스의 encode라는 메서드에 (쿠키값:코딩 할 값, "utf-8")를 사용하면 공백을 바꿔줌. replaceAll까지 외워서 사용하면 될 듯.

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, cookieValue); // (Name,Value)   //쿠키를 만들 수 있게, 스프링에서 쿠키 클래스를 제공함.
            cookie.setPath("/");
            cookie.setMaxAge(30 * 60);  //만료기한

            // Response 객체에 Cookie 추가 (Response에다가 데이터를 담으면 클라이언트에게 전달이 되기 때문에)
            res.addCookie(cookie);    //addCookie: 쿠키를 추가하는 메서드
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
