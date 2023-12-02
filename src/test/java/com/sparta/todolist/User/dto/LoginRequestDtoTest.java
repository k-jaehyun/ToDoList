package com.sparta.todolist.User.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDtoTest {

    @Test
    void testGetter() {
        // Given
        String username="testUserName";
        String password="testPW";
        LoginRequestDto loginRequestDto = new LoginRequestDto(username,password);

        // When-Then
        assertEquals("testUserName", loginRequestDto.getUsername());
        assertEquals("testPW", loginRequestDto.getPassword());
    }
}