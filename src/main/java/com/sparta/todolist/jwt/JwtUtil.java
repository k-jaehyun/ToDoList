package com.sparta.todolist.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String username) {
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER,token);
            cookie.setPath("/");

            res.addCookie(cookie);

        } catch (UnsupportedEncodingException e ) {
            log.error("토큰 생성 실패");
        }
    }

//    // header 에서 JWT 가져오기
//    public String getJwtFromHeader(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    // 토큰 검증
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (SecurityException | MalformedJwtException | SignatureException e) {
//            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
//        } catch (ExpiredJwtException e) {
//            log.error("Expired JWT token, 만료된 JWT token 입니다.");
//        } catch (UnsupportedJwtException e) {
//            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
//        } catch (IllegalArgumentException e) {
//            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
//        }
//        return false;
//    }
//
//    // 토큰에서 사용자 정보 가져오기
//    public Claims getUserInfoFromToken(String token) {
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//    }
}